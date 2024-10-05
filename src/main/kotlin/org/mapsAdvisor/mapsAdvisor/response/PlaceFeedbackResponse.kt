package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.entity.PlaceFeedback

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