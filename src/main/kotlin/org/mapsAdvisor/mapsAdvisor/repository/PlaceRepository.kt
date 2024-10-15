package org.mapsAdvisor.mapsAdvisor.repository


import com.mongodb.client.model.geojson.Point
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlaceRepository : MongoRepository<Place, String> {
    override fun findAll(pageable: Pageable): Page<Place>

    //@Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}}")
    fun findByCoordinatesNear(
        coordinates: org.springframework.data.geo.Point,
        pageable: Pageable
    ): Page<Place>

    //@Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}, tags: {\$in: [?2]}}")
    fun findByCoordinatesNearAndTagsContains(
        coordinates: org.springframework.data.geo.Point,
        tag: String,
        pageable: Pageable
    ): Page<Place>

    //@Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}, name: {\$regex: ?2, \$options: 'i'}}")
    fun findByCoordinatesNearAndNameContains(
        coordinates: org.springframework.data.geo.Point,
        name: String,
        pageable: Pageable
    ): Page<Place>

    //@Query("{location:{\$nearSphere:{\$geometry:{type:'Point',coordinates:[?1,?0]},\$minDistance:1,\$maxDistance:1000}}, owners: {\$in: [?2]}}")
    /*fun findByCoordinatesNearAndOwnersContains(
        coordinates: org.springframework.data.geo.Point,
        owners: List<String>,
    ): Place?*/

    fun deleteAllByOwnersContains(personId: String)
}