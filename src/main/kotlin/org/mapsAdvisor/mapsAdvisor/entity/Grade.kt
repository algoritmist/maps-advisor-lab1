package org.mapsAdvisor.mapsAdvisor.entity

import org.springframework.data.mongodb.core.mapping.Field

data class Grade(
    @Field("person_id")
    var personId: String,

    var grade: Int
)
