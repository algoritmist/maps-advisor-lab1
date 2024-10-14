package org.mapsAdvisor.mapsAdvisor.entity

import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("route")
data class Route(
    @Id
    val id: String? = null,

    @Size(min = 1, max = 100)
    var name: String,
    var description: String,

    @Size(min = 1)
    var places: List<String> = listOf() // references
)