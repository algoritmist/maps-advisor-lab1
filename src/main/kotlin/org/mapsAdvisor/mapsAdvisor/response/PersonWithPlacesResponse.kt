package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.mapsAdvisor.mapsAdvisor.entity.Place

data class PersonWithPlacesResponse(
    val username: String,
    val places: List<PlaceResponse>
) {
    companion object {
        fun fromEntities(person: Person, places: List<Place>): PersonWithPlacesResponse =
            PersonWithPlacesResponse(
                username = person.username,
                places = places.map { place -> PlaceResponse.fromEntity(place) }
            )
    }
}