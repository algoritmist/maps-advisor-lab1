package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("route")
data class Route(
    @Id
    val id: String? = null,
    var name: String,
    var description: String,
    @Field("places")
    var places: List<String> = listOf() // references
)