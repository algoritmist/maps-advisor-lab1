package org.mapsAdvisor.mapsAdvisor.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class PersonRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,

    @field:NotBlank(message = "Username must not be blank")
    @field:Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    val username: String,

    @field:NotBlank(message = "Password must not be blank")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    @field:NotBlank(message = "Role must not be blank")
    @field:Pattern(regexp = "USER|ADMIN|OWNER", message = "Role must be either USER, ADMIN, or OWNER")
    val role: String,

    @JsonProperty("places_owned") val placesOwned: List<String> = listOf(),
)
