package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.PlaceFeedback

class PlaceFeedbackResponse(
    val id: String,
    val placeId: String,
    val personId: String,
    val grade: Int,
) {
    companion object {
        fun fromEntity(placeFeedback: PlaceFeedback): PlaceFeedbackResponse =
            PlaceFeedbackResponse(
                id = placeFeedback.id!!,
                placeId = placeFeedback.placeId,
                personId = placeFeedback.grade.personId,
                grade = placeFeedback.grade.grade,
            )
    }
}