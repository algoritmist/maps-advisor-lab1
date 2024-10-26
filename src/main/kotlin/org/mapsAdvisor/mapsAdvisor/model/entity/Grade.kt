package org.mapsAdvisor.mapsAdvisor.model.entity

import org.springframework.data.mongodb.core.mapping.Field

data class Grade(
    @Field("person_id")
    var personId: String,
    @Field("grade")
    var grade: Int
)
