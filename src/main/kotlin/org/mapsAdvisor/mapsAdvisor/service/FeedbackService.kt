package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.model.Grade
import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceFeedbackRequest

class FeedbackService(
    private val routeFeedbackRepository: RouteFeedbackRepository,
    private val placeFeedbackRepository: PlaceFeedbackRepository
) {
}