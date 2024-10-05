package org.mapsAdvisor.mapsAdvisor.model

class ErrorMessage(
    var status: Int? = null,
    var message: String? = null,
    val exception: String? = null,
)