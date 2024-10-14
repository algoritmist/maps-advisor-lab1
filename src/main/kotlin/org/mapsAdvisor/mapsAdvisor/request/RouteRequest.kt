package org.mapsAdvisor.mapsAdvisor.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RouteRequest(
    @field:NotBlank(message = "Название не может быть пустым")
    @field:Size(min = 3, max = 100, message = "Название должно быть от 3 до 100 символов")
    val name: String,
    val description: String,

    @field:Size(min = 1, message = "Должен быть указан хотя бы один пункт маршрута")
    val places: MutableList<String>
)