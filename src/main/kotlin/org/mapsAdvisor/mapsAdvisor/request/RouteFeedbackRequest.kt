package org.mapsAdvisor.mapsAdvisor.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class RouteFeedbackRequest(
    @field:NotBlank(message = "routeId must not be blank")
    @JsonProperty("route_id") val routeId: String,

    @field:NotBlank(message = "personId must not be blank")
    @JsonProperty("person_id") val personId: String,

    @field:NotNull(message = "Grade must not be blank")
    @field:Min(value = 1, message = "Grade must be at least 1")
    @field:Max(value = 5, message = "Grade cannot be more than 5")
    val grade: Int,
)