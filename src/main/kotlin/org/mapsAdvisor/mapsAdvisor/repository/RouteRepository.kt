package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.model.Route
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RouteRepository : MongoRepository<Route, String> {
    override fun findAll(pageable: Pageable): Page<Route>
}