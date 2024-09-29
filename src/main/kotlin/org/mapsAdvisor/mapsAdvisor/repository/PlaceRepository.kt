package org.mapsAdvisor.mapsAdvisor.repository


import org.mapsAdvisor.mapsAdvisor.model.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point

import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceRepository : MongoRepository<Place, String> {
    override fun findAll(pageable: Pageable): Page<Place>
    fun findByLocationNear(
        point: Point,
        distance: Distance,
        pageable: Pageable
    ): Page<Place>

    fun findByCoordinatesNearAndTagsContaining(
        point: Point,
        distance: Distance,
        tag: String,
        pageable: Pageable
    ): Page<Place>

    fun findByCoordinatesNearAndNameContaining(
        point: Point,
        distance: Distance,
        name: String,
        pageable: Pageable
    ): Page<Place>
}