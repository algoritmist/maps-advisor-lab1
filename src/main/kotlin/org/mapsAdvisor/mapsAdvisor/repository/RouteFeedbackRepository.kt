package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.entity.RouteFeedback
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository

interface RouteFeedbackRepository: MongoRepository<RouteFeedback, String> {
    fun findByRouteId(routeId: String, page: Pageable): Page<RouteFeedback>
    fun deleteAllByRouteId(routeId: String)
}