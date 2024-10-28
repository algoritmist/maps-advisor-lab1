package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.Person

data class UserResponse(
    val id: String,
    val username: String,
    val role: String,
    val placesOwned: List<String>,
) {
    companion object {
        fun fromEntity(person: Person): UserResponse =
            UserResponse(
                id = person.id!!,
                username = person.username,
                role = person.role.toString(),
                placesOwned = person.placesOwned,
            )
    }
}