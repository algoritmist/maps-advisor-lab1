package org.mapsAdvisor.mapsAdvisor.request

data class PlaceRequest(
    val name: String,
    val coordinates: Coordinates,
    val tags: List<String> = listOf(),
    val owners: List<String> = listOf(),
    val info: String? = null
)

data class Coordinates(
    val longitude: Double,
    val latitude: Double
)