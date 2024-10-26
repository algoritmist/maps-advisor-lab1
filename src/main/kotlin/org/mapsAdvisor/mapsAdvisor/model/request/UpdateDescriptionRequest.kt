package org.mapsAdvisor.mapsAdvisor.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateDescriptionRequest(
    @field:Size(max = 500, message = "description should be no more than 500 characters")
    val description: String
)