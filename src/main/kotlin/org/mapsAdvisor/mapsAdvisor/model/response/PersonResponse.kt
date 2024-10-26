package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.Person

data class PersonResponse(
    val id: String,
    val username: String,
    val role: String,
    val placesOwned: List<String>,
) {
    companion object {
        fun fromEntity(person: Person): PersonResponse =
            PersonResponse(
                id = person.id!!,
                username = person.username,
                role = person.role.toString(),
                placesOwned = person.placesOwned,
            )
    }
}