package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.model.FavoriteEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface FavoritesRepository : MongoRepository<FavoriteEntity, String> {
    fun findByPersonId(personId: String): List<FavoriteEntity>
}