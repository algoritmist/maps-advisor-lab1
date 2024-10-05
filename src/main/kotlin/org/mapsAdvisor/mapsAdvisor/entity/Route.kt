package org.mapsAdvisor.mapsAdvisor.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("route")
data class Route(
    @Id
    val id: String? = null,
    var name: String,
    var description: String,
    var places: List<String> = listOf() // references
)