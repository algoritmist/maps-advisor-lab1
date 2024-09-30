package org.mapsAdvisor.mapsAdvisor.request

data class FavoritesRequest(
    var personId: String,
    var placeId: String,
    var favoriteType: String
)
