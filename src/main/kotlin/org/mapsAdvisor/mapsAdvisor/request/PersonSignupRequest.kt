package org.mapsAdvisor.mapsAdvisor.request

data class PersonSignupRequest(
    val username: String,
    val name: String,
    val password: String
)