package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.entity.Grade
import org.mapsAdvisor.mapsAdvisor.entity.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.entity.RouteFeedback
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.*
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteFeedbackRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val routeFeedbackRepository: RouteFeedbackRepository,
    private val placeFeedbackRepository: PlaceFeedbackRepository,
    private val routeRepository: RouteRepository,
    private val placeRepository: PlaceRepository,
    private val personRepository: PersonRepository,
) {
    fun createRouteFeedback(feedback: CreateRouteFeedbackRequest): RouteFeedback {
        if (!routeRepository.existsById(feedback.routeId)) {
            throw NotFoundException("Route with id ${feedback.routeId} not found")
        }
        if (!personRepository.existsById(feedback.personId)) {
            throw NotFoundException("Person with id ${feedback.personId} not found")
        }
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

    fun getRouteFeedbacks(routeId: String, page: Int, size: Int): List<RouteFeedback> {
        if (!routeRepository.existsById(routeId)) {
            throw NotFoundException("Route with id $routeId not found")
        }
        val pageable: Pageable = PageRequest.of(page, size)
        return routeFeedbackRepository.findByRouteId(routeId, pageable).content
    }

    fun deleteRouteFeedback(feedbackId: String) {
        if (!routeFeedbackRepository.existsById(feedbackId)) {
            throw NotFoundException("Feedback with id $feedbackId not found")
        }
        routeFeedbackRepository.deleteById(feedbackId)
    }

    fun createPlaceFeedback(feedback: CreatePlaceFeedbackRequest): PlaceFeedback {
        if (!placeRepository.existsById(feedback.placeId)) {
            throw NotFoundException("Place with id ${feedback.placeId} not found")
        }
        if (!personRepository.existsById(feedback.personId)) {
            throw NotFoundException("Person with id ${feedback.personId} not found")
        }
        return placeFeedbackRepository.save(
            PlaceFeedback(
                placeId = feedback.placeId,
                grade = Grade(
                    personId = feedback.personId,
                    grade = feedback.grade,
                )
            )
        )
    }

    fun getPlaceFeedbacks(placeId: String, page: Int, size: Int): List<PlaceFeedback> {
        if (!placeFeedbackRepository.existsById(placeId)) {
            throw NotFoundException("Place with id $placeId not found")
        }
        val pageable: Pageable = PageRequest.of(page, size)
        return placeFeedbackRepository.findByPlaceId(placeId, pageable).content
    }

    fun deletePlaceFeedback(feedbackId: String) {
        if (!placeFeedbackRepository.existsById(feedbackId)) {
            throw NotFoundException("Feedback with id $feedbackId not found")
        }
        placeFeedbackRepository.deleteById(feedbackId)
    }

}
