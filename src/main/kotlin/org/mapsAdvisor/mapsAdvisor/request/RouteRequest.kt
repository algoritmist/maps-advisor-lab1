package org.mapsAdvisor.mapsAdvisor.request

data class RouteRequest(
    val name: String,
    val description: String,
    val places: MutableList<String>
) {
}