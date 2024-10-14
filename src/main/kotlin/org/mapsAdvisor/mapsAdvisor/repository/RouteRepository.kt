package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface RouteRepository : MongoRepository<Route, String> {
    override fun findAll(pageable: Pageable): Page<Route>
    @Query("{ 'places': ?0 }")
    fun findAllByPlacesContains(placeId: String): List<Route>
}