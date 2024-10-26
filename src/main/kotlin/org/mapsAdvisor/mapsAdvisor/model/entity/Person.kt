package org.mapsAdvisor.mapsAdvisor.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("person")
data class Person(
    @Id
    val id: String? = null,
    @Field("name")
    var name: String,
    @Field("username")
    var username: String,
    @Field("password")
    var password: String,
    @Field("role")
    var role: Role,
    @Field("places_owned")
    var placesOwned: List<PlaceId> = listOf(),
    @Field("registration_date")
    var registrationDate: Instant,
)

enum class Role {
    USER,
    ADMIN,
    OWNER
}