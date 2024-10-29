package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.model.entity.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PlaceRepository : MongoRepository<Place, String> {
    override fun findAll(pageable: Pageable): Page<Place>

    fun findByCoordinatesNear(coordinates: Point, distance: Distance, pageable: Pageable): Page<Place>

    fun findByCoordinatesNearAndTagsContains(coordinates: Point,  tag: String, distance: Distance, pageable: Pageable): Page<Place>

    fun findByCoordinatesNearAndNameContains(coordinates: Point, name: String, distance: Distance, pageable: Pageable): Page<Place>

    fun findByCoordinates(coordinates: GeoJsonPoint): Optional<Place>

    fun deleteAllByOwnersContains(personId: String)
}