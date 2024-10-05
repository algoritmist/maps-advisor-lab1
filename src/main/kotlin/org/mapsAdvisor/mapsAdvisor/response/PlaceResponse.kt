package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.entity.Place


class PlaceResponse(
    val id: String,
    val name: String,
    val tags: List<String>
) {
    companion object {
        fun fromEntity(place: Place): PlaceResponse =
            PlaceResponse(
                id = place.id!!,
                name = place.name,
                tags = place.tags
            )
    }
}