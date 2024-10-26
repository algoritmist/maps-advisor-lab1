package org.mapsAdvisor.mapsAdvisor.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserRequest(
    @field:NotBlank(message = "name must not be blank")
    @field:Size(min = 2, max = 50, message = "name must be between 2 and 50 characters")
    val name: String,

    @field:NotBlank(message = "username must not be blank")
    @field:Size(min = 3, max = 30, message = "username must be between 3 and 30 characters")
    val username: String,

    @field:NotBlank(message = "password must not be blank")
    @field:Size(min = 8, message = "password must be at least 8 characters long")
    val password: String
)
