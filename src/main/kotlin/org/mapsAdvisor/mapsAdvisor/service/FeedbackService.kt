package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.model.Grade
import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.model.RouteFeedback
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.RouteFeedbackRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val routeFeedbackRepository: RouteFeedbackRepository,
    private val placeFeedbackRepository: PlaceFeedbackRepository
) {
    companion object {
        const val SIZE: Int = 50
    }

    fun createRouteFeedback(feedback: RouteFeedbackRequest): RouteFeedback {
        return routeFeedbackRepository.save(
            RouteFeedback(
                routeId = feedback.routeId,
                grade = Grade(
                    personId = feedback.personId,
                    grade = feedback.grade,
                )
            )
        )
    }

    fun getRouteFeedbacks(routeId: String, page: Int): Page<RouteFeedback> {
        val pageable: Pageable = PageRequest.of(page, SIZE)
        return routeFeedbackRepository.findByRouteId(routeId, pageable)
    }

    fun deleteRouteFeedback(feedbackId: String) {
        routeFeedbackRepository.deleteById(feedbackId)
    }

    fun createPlaceFeedback(feedback: PlaceFeedbackRequest): PlaceFeedback {
        return placeFeedbackRepository.save(
            PlaceFeedback(
                placeId = feedback.routeId,
                grade = Grade(
                    personId = feedback.personId,
                    grade = feedback.grade,
                )
            )
        )
    }

    fun getPlaceFeedbacks(routeId: String, page: Int): Page<PlaceFeedback> {
        val pageable: Pageable = PageRequest.of(page, SIZE)
        return placeFeedbackRepository.findByPlaceId(routeId, pageable)
    }

    fun deletePlaceFeedback(feedbackId: String) {
        placeFeedbackRepository.deleteById(feedbackId)
    }

}
