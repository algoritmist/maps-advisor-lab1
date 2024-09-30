package org.mapsAdvisor.mapsAdvisor.request

import jakarta.validation.Valid
import jakarta.validation.constraints.*

data class PlaceRequest(
    @field:NotBlank(message = "Название не может быть пустым")
    @field:Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
    val name: String,

    @field:Valid
    val coordinates: Coordinates,
    @field:Size(max = 10, message = "Не более 10 тегов")
    val tags: List<String> = listOf(),
    val owners: List<String> = listOf(),
    @field:Size(max = 500, message = "Информация должна быть не более 500 символов")
    val info: String? = null
)

data class Coordinates(
    @field:NotNull(message = "Долгота не может быть пустой")
    @field:DecimalMin(value = "-180.0", message = "Долгота должна быть не меньше -180")
    @field:DecimalMax(value = "180.0", message = "Долгота должна быть не больше 180")
    val longitude: Double,

    @field:NotNull(message = "Широта не может быть пустой")
    @field:DecimalMin(value = "-90.0", message = "Широта должна быть не меньше -90")
    @field:DecimalMax(value = "90.0", message = "Широта должна быть не больше 90")
    val latitude: Double
)