package org.mapsAdvisor.mapsAdvisor.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class FavoritesRequest(
    @field:NotBlank(message = "Person ID cannot be blank")
    var personId: String,

    @field:NotBlank(message = "Place ID cannot be blank")
    var placeId: String,

    @field:NotBlank(message = "Favorite type cannot be blank")
    @field:Pattern(regexp = "HOME|WORK|ENTERTAINMENT", message = "Type must be either HOME, WORK, or ENTERTAINMENT")
    @JsonProperty("favorite_type") var favoriteType: String
)
