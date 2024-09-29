package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("person")
data class Person(
    @Id
    val id: String? = null,

    var fullName: String,

    var username: String,

    var password: String,

    var role: Role,

    var places: List<String> = listOf(),

    var registrationDate: Date
)

enum class Role {
    USER,
    ADMIN,
    OWNER
}