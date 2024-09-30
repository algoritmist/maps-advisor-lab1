package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceService(
    private val placeRepository: PlaceRepository,
    private val placeFeedbackRepository: PlaceFeedbackRepository
) {
    companion object {
        const val SIZE: Int = 50
    }

    fun createPlace(request: PlaceRequest): Place =
        placeRepository.save(
            Place(
                name = request.name,
                coordinates = GeoJsonPoint(request.coordinates.longitude, request.coordinates.latitude),
                tags = request.tags,
                owners = request.owners,
                info = request.info
            )
        )

    fun findAll(page: Int): List<Place> {
        val pageable = PageRequest.of(page, SIZE)
        return placeRepository.findAll(pageable).content
    }

    fun findById(id: String): Place =
        placeRepository.findById(id)
            .orElseThrow { NotFoundException("Place with id $id not found") }

    fun findByLocationNear(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        page: Int
    ): List<Place> {
        val point = Point(longitude, latitude)
        val distance = Distance(distanceKm)
        val pageable = PageRequest.of(page, SIZE)
        return placeRepository.findByLocationNear(point, distance, pageable).content
    }

    fun findNearbyPlacesWithTag(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        tag: String,
        page: Int
    ): List<Place> {
        val point = Point(longitude, latitude)
        val distance = Distance(distanceKm)
        val pageable = PageRequest.of(page, SIZE)
        return placeRepository.findByCoordinatesNearAndTagsContaining(point, distance, tag, pageable).content
    }

    fun findNearbyPlacesByName(
        latitude: Double,
        longitude: Double,
        distanceKm: Double,
        name: String,
        page: Int): List<Place> {
        val point = Point(longitude, latitude)
        val distance = Distance(distanceKm)
        val pageable = PageRequest.of(page, SIZE)
        return placeRepository.findByCoordinatesNearAndNameContaining(point, distance, name, pageable).content
    }

    @Transactional
    fun deleteById(id: String) {
        val placeToDelete = findById(id)

        placeRepository.delete(placeToDelete)

        placeFeedbackRepository.deleteAllByPlaceId(id)
    }

    fun countAll(): Long {
        return placeRepository.count()
    }
}