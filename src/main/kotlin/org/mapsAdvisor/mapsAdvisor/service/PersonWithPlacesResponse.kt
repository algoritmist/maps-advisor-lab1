package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.model.Person
import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.response.PlaceResponse

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