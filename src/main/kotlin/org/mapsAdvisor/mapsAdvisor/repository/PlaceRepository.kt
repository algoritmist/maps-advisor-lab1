package org.mapsAdvisor.mapsAdvisor.repository


import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    @Query("{ 'coordinates' : ?0, 'owners' : ?1 }")
    fun findByCoordinatesAndOwnersContaining(
        coordinates: GeoJsonPoint, owners: List<String>,
    ): Place?

    fun deleteAllByOwnersContains(personId: String)
}