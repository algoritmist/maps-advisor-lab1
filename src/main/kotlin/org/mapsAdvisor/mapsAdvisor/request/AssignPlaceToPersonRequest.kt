package org.mapsAdvisor.mapsAdvisor.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AssignPlaceToPersonRequest(
    @field:NotBlank(message = "place id must not be blank")
    val placeId: String,

    @field:NotBlank(message = "person id must not be blank")
    val personId: String
)