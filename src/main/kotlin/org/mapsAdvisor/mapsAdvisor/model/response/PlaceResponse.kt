package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.Place

data class PlaceResponse(
    val id: String,
    val name: String,
    val coordinates: Coordinates,
    val tags: List<String>,
    val owners: List<String>,

) {
    companion object {
        fun fromEntity(place: Place): PlaceResponse =
            PlaceResponse(
                id = place.id!!,
                name = place.name,
                coordinates = Coordinates(longitude = place.coordinates.x, latitude = place.coordinates.y),
                owners = place.owners,
                tags = place.tags
            )
    }
}

data class Coordinates(
    val longitude: Double,
    val latitude: Double
)