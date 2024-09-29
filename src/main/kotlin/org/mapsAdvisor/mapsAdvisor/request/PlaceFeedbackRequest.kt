package org.mapsAdvisor.mapsAdvisor.request

data class PlaceFeedbackRequest(
    val placeId: String,
    val personId: String,
    val grade: Int
)