package org.mapsAdvisor.mapsAdvisor.request

data class PersonRequest(
    val name: String,
    val username: String,
    val password: String,
    val role: String,
    val placesOwned: List<String> = listOf(),
)
