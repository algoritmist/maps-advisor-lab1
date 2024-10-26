package org.mapsAdvisor.mapsAdvisor.model.response

import org.mapsAdvisor.mapsAdvisor.model.entity.Route

class RouteResponse(
    val id: String,
    val name: String,
    val places: List<String>
) {
    companion object {
        fun fromEntity(route: Route): RouteResponse =
            RouteResponse(
                id = route.id!!,
                name = route.name,
                places = route.places
            )
    }
}