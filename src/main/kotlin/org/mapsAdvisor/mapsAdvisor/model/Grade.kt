package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.mongodb.core.mapping.Field

data class Grade(
    @Field("person_id")
    var personId: String,

    var grade: Int
)
