package org.mapsAdvisor.mapsAdvisor.response

import org.mapsAdvisor.mapsAdvisor.entity.FavoriteEntity

data class FavoritesResponse(
    val id: String,
    val personId: String,
    val placeId: String,
    val favoriteType: String
) {
    companion object {
        fun fromEntity(favoriteEntity: FavoriteEntity): FavoritesResponse =
            FavoritesResponse(
                id = favoriteEntity.id!!,
                placeId = favoriteEntity.placeId,
                personId = favoriteEntity.personId,
                favoriteType = favoriteEntity.favorites.toString()
            )
    }
}
