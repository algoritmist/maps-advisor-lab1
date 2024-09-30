package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.model.Favorite
import org.mapsAdvisor.mapsAdvisor.model.FavoriteEntity
import org.mapsAdvisor.mapsAdvisor.model.Grade
import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.springframework.stereotype.Service

@Service
class FavoritesService(
    private val favoritesRepository: FavoritesRepository
) {
    fun saveFavorite(favorite: FavoritesRequest): FavoriteEntity {
        return favoritesRepository.save(
            FavoriteEntity(
                personId = favorite.personId,
                placeId = favorite.placeId,
                favorites = Favorite.valueOf(favorite.favoriteType)
            )
        )
    }

    fun getFavoriteById(id: String): FavoriteEntity? {
        return favoritesRepository.findById(id).orElse(null)
    }

    fun getFavoritesByPersonId(personId: String): List<FavoriteEntity> {
        return favoritesRepository.findByPersonId(personId)
    }

    fun deleteFavorite(id: String) {
        favoritesRepository.deleteById(id)
    }
}