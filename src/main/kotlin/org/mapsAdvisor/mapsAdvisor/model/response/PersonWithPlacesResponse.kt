package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.Person
import org.mapsAdvisor.mapsAdvisor.model.entity.Place

data class PersonWithPlacesResponse(
    val id: String,
    val username: String,
    val role: String,
    val placesOwned: List<String>,
    val place: PlaceResponse
) {
    companion object {
        fun fromEntity(person: Person, place: Place): PersonWithPlacesResponse =
            PersonWithPlacesResponse(
                id = person.id!!,
                username = person.username,
                role = person.role.toString(),
                placesOwned = person.placesOwned,
                place = PlaceResponse.fromEntity(place)
            )
    }
}