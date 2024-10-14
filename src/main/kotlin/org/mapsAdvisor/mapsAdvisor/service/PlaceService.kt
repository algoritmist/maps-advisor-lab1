package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val placeFeedbackRepository: PlaceFeedbackRepository,
    private val personRepository: PersonRepository,
    private val favoritesRepository: FavoritesRepository
) {
    @Transactional
    fun createPlace(request: PlaceRequest): Place {
        val owners = personRepository.findAllById(request.owners)
        if (owners.size != request.owners.size) {
            throw NotFoundException("One or more owners not found")
        }
        val existingPlace = placeRepository.findByCoordinatesAndOwnersContaining(
            GeoJsonPoint(request.coordinates.longitude, request.coordinates.latitude),
            request.owners
        )
        if (existingPlace != null) {
            throw IllegalArgumentException("Place has already been added to that owner")
        }

        return placeRepository.save(
            Place(
                name = request.name,
                coordinates = GeoJsonPoint(request.coordinates.longitude, request.coordinates.latitude),
                tags = request.tags,
                owners = request.owners,
                info = request.info
            )
        )
    }

    fun findAll(page: Int, size: Int): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findAll(pageable).content
    }

    fun findById(id: String): Place =
        placeRepository.findById(id)
            .orElseThrow { NotFoundException("Place with id $id not found") }

    fun findByLocationNear(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findAllByCoordinates(latitude, longitude, pageable).content
    }

    fun findNearbyPlacesWithTag(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        tag: String,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findAllByCoordinatesAndTagsContaining(latitude, longitude, tag, pageable).content
    }

    fun findNearbyPlacesByName(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        name: String,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findAllByCoordinatesAndName(latitude, longitude, name, pageable).content
    }

    @Transactional
    fun deleteById(id: String) {
        try {
            val placeToDelete = findById(id)

            placeRepository.delete(placeToDelete)

            placeFeedbackRepository.deleteAllByPlaceId(id)

            val favoritesToDelete = favoritesRepository.findAllByPlaceId(id)
            favoritesToDelete.forEach { favorite ->
                favoritesRepository.delete(favorite)
            }

            val owners = personRepository.findAllByPlacesOwnedContains(id)
            owners.forEach { person ->
                person.placesOwned = person.placesOwned.filter { it != id }
                personRepository.save(person)
            }
        } catch (ex: NotFoundException) {
            throw ex
        } catch (ex: DataAccessException) {
            throw IllegalStateException("Failed to delete place or associated records", ex)
        }
    }

    fun countAll(): Long {
        return placeRepository.count()
    }
}