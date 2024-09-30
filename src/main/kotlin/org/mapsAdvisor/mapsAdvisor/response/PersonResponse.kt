package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.model.Person
import org.mapsAdvisor.mapsAdvisor.model.Place

data class PersonResponse(
    val username: String
) {
    companion object {
        fun fromEntity(person: Person): PersonResponse =
            PersonResponse(
                username = person.username
            )
    }
}