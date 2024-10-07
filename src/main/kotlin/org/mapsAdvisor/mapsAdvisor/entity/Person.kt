package org.mapsAdvisor.mapsAdvisor.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

typealias PlaceId = String

@Document("person")
data class Person(
    @Id
    val id: String? = null,
    var name: String,
    var username: String,
    var password: String,
    var role: Role,
    @Field("places_owned")
    var placesOwned: List<PlaceId> = listOf(), // references
    @Field("registration_date")
    var registrationDate: Instant,
)

enum class Role {
    USER,
    ADMIN,
    OWNER
}