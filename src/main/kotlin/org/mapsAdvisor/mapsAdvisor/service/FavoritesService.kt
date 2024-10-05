package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.entity.Favorite
import org.mapsAdvisor.mapsAdvisor.entity.FavoriteEntity
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FavoritesService(
    private val favoritesRepository: FavoritesRepository,
    private val placeRepository: PlaceRepository,
    private val personRepository: PersonRepository,
) {
    fun saveFavorite(favorite: FavoritesRequest): FavoriteEntity {
        if (!placeRepository.existsById(favorite.placeId)) {
            throw NotFoundException("Place with id ${favorite.placeId} not found")
        }

        if (!personRepository.existsById(favorite.personId)) {
            throw NotFoundException("Person with id ${favorite.personId} not found")
        }

        val favoriteType = try {
            Favorite.valueOf(favorite.favoriteType.uppercase())
        } catch (ex: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid favorite type: ${favorite.favoriteType}")
        }

        return favoritesRepository.save(
            FavoriteEntity(
                personId = favorite.personId,
                placeId = favorite.placeId,
                favorites = favoriteType
            )
        )
    }

    fun getFavoriteById(id: String): FavoriteEntity {
        return favoritesRepository.findById(id)
            .orElseThrow { NotFoundException("Favorite with id $id not found") }
    }

    fun getFavoritesByPersonId(personId: String, page: Int, size: Int): List<FavoriteEntity> {
        if (!personRepository.existsById(personId)) {
            throw NotFoundException("Person with id $personId not found")
        }
        val pageable: Pageable = PageRequest.of(page, size)
        return favoritesRepository.findByPersonId(personId, pageable).content
    }

    fun deleteFavorite(id: String) {
        if (!favoritesRepository.existsById(id)) {
            throw NotFoundException("Favorite with id $id not found")
        }
        favoritesRepository.deleteById(id)
    }
}