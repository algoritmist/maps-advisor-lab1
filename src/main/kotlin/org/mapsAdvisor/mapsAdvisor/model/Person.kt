package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document("person")
data class Person(
    @Id
    val id: String? = null,
    var name: String,
    var username: String,
    var password: String,
    var role: Role,
    var placesOwned: List<String> = listOf(), // references
    var registrationDate: Instant,
)

enum class Role {
    USER,
    ADMIN,
    OWNER
}