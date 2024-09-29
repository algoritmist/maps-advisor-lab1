package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.model.Route

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