package org.mapsAdvisor.mapsAdvisor.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PlaceFeedbackRequest(
    @field:NotBlank(message = "placeId не может быть пустым")
    val placeId: String,

    @field:NotBlank(message = "personId не может быть пустым")
    val personId: String,

    @field:NotNull(message = "Grade не может быть пустым")
    @field:Min(value = 1, message = "Grade must be at least 1")
    @field:Max(value = 5, message = "Grade cannot be more than 5")
    val grade: Int,
)