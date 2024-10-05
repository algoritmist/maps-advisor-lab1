package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.entity.RouteFeedback

class RouteFeedbackResponse(
    val id: String,
    val routeId: String,
    val personId: String,
    val grade: Int,
) {
    companion object {
        fun fromEntity(routeFeedback: RouteFeedback): RouteFeedbackResponse =
            RouteFeedbackResponse(
                id = routeFeedback.id!!,
                routeId = routeFeedback.routeId,
                personId = routeFeedback.grade.personId,
                grade = routeFeedback.grade.grade,
            )
    }
}