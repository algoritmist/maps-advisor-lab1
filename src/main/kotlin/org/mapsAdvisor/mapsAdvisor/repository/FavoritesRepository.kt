package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.entity.FavoriteEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoritesRepository : MongoRepository<FavoriteEntity, String> {
    fun findByPersonId(personId: String, pageable: Pageable): Page<FavoriteEntity>
    fun findAllByPlaceId(placeId: String): List<FavoriteEntity>
}