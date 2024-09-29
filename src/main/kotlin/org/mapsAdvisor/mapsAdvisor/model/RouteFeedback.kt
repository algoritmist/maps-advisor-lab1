package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("grades")
data class RouteFeedback(
    @Id
    val id: String? = null,

    @Field("route_id")
    var routeId: String,

    @Field("grade")
    var grade: Grade
)

data class Grade(
    @Field("person_id")
    var personId: String,

    var grade: Int
)
