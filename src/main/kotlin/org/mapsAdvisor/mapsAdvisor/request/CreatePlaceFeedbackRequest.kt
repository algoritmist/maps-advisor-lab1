package org.mapsAdvisor.mapsAdvisor.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreatePlaceFeedbackRequest(
    @field:NotBlank(message = "place id must not be blank")
    val placeId: String,

    @field:NotBlank(message = "person id must not be blank")
    val personId: String,

    @field:NotNull(message = "grade must not be blank")
    @field:Min(value = 1, message = "grade must be at least 1")
    @field:Max(value = 5, message = "grade cannot be more than 5")
    val grade: Int,
)