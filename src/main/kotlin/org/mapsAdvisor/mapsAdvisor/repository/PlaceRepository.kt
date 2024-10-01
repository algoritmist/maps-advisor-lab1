package org.mapsAdvisor.mapsAdvisor.repository


import org.mapsAdvisor.mapsAdvisor.model.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : MongoRepository<Place, String> {
    override fun findAll(pageable: Pageable): Page<Place>

    @Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}}")
    fun findAllByCoordinates(
        latitude: Double,
        longitude: Double,
        pageable: Pageable
    ): Page<Place>

    @Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}}")
    fun findAllByCoordinatesAndTagsContaining(
        latitude: Double,
        longitude: Double,
        tag: String,
        pageable: Pageable
    ): Page<Place>

    @Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}}")
    fun findAllByCoordinatesAndName(
        latitude: Double,
        longitude: Double,
        name: String,
        pageable: Pageable
    ): Page<Place>
}