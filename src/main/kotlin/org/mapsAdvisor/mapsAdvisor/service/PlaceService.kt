package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.DuplicateException
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.entity.Place
import org.mapsAdvisor.mapsAdvisor.model.entity.Role
import org.mapsAdvisor.mapsAdvisor.model.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.repository.findByIdOrNull
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
    fun createPlace(request: CreatePlaceRequest): Place {
        val existingPlace = placeRepository.findByCoordinates(
            GeoJsonPoint(request.coordinates.longitude, request.coordinates.latitude)
        )

        if (existingPlace.isPresent) {
            throw DuplicateException("Place has already been added")
        }

        if (request.owners.isNotEmpty()) {
            request.owners.forEach { id ->
                if (!personRepository.existsById(id)) {
                    throw NotFoundException("Person with id $id not found")
                }
            }
        }

        val newPlace = Place(
            name = request.name,
            coordinates = GeoJsonPoint(request.coordinates.longitude, request.coordinates.latitude),
            tags = request.tags,
            owners = request.owners,
            description = request.description
        )

        val savedPlace = placeRepository.save(newPlace)

        if (request.owners.isEmpty()) {
            request.owners.forEach { id ->
                val personOptional = personRepository.findById(id)
                personOptional.ifPresent { person ->
                    if (!person.placesOwned.contains(savedPlace.id)) {
                        person.placesOwned += savedPlace.id!!
                        if (person.role == Role.USER) {
                            person.role = Role.OWNER
                        }
                        personRepository.save(person)
                    }
                }

            }
        }

        return savedPlace
    }

    fun getAllPlaces(page: Int, size: Int): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findAll(pageable).content
    }

    fun getPlaceById(id: String): Place? =
        placeRepository.findByIdOrNull(id)

    fun getPlacesNear(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findByCoordinatesNear(Point(longitude, latitude), pageable).content
    }

    fun getPlacesNearByTag(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        tag: String,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findByCoordinatesNearAndTagsContains(Point(longitude, latitude), tag, pageable).content
    }

    fun getPlacesNearByName(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        name: String,
        page: Int,
        size: Int
    ): List<Place> {
        val pageable = PageRequest.of(page, size)
        return placeRepository.findByCoordinatesNearAndNameContains(Point(longitude, latitude), name, pageable).content
    }

    fun updateDescription(id: String, description: String): Place {
        val place = getPlaceById(id) ?: throw NotFoundException("Place with id $id not found")
        place.description = description

        return placeRepository.save(place)
    }

    @Transactional
    fun deleteById(id: String) {
        val placeToDelete = getPlaceById(id) ?: throw NotFoundException("Place with id $id not found")
        placeRepository.delete(placeToDelete)

        if (placeFeedbackRepository.existsByPlaceId(id)) {
            placeFeedbackRepository.deleteAllByPlaceId(id)
        }

        if (favoritesRepository.existsByPlaceId(id)) {
            favoritesRepository.deleteAllByPlaceId(id)
        }

        val owners = personRepository.findAllByPlacesOwnedContains(id)
        owners.forEach { person ->
            person.placesOwned = person.placesOwned.filter { it != id }
            personRepository.save(person)
        }
    }
}