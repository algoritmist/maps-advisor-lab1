package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.model.RouteFeedback

class PlaceFeedbackResponse(
    val id: String,
    val routeId: String,
    val personId: String,
    val grade: Int,
) {
    companion object {
        fun fromEntity(placeFeedback: PlaceFeedback): PlaceFeedbackResponse =
            PlaceFeedbackResponse(
                id = placeFeedback.id!!,
                routeId = placeFeedback.placeId,
                personId = placeFeedback.grade.personId,
                grade = placeFeedback.grade.grade,
            )
    }
}