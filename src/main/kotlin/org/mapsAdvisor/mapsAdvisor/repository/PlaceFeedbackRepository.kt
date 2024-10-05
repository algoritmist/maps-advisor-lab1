package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.entity.PlaceFeedback
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceFeedbackRepository: MongoRepository<PlaceFeedback, String> {
    fun findByPlaceId(routeId: String, page: Pageable): Page<PlaceFeedback>
    fun deleteAllByPlaceId(placeId: String)
}