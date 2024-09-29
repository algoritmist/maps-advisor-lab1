package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.model.RouteFeedback
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceFeedbackRepository: MongoRepository<PlaceFeedback, String> {
    fun findByPlaceId(routeId: String, page: Pageable): Page<PlaceFeedback>
}