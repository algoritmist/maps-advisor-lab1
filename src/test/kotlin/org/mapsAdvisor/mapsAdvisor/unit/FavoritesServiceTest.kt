package org.mapsAdvisor.mapsAdvisor.unit

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.entity.*
import org.mapsAdvisor.mapsAdvisor.model.request.CreateFavoritesRequest
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mapsAdvisor.mapsAdvisor.service.UserService
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class FavoritesServiceTest {
    private val favoritesRepository = mock<FavoritesRepository>()
    private val placeService = mock<PlaceService>()
    private val personService = mock<UserService>()

    private val favoritesService = FavoritesService(favoritesRepository, personService, placeService)

    @Test
    fun `test saveFavorite with valid data`() {
        val personId = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        val favoriteId = UUID.randomUUID().toString()
        whenever(placeService.getPlaceById(placeId)).thenReturn(Place(placeId, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(personService.getUserById(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        val favoriteEntity = FavoriteEntity(
            id = favoriteId,
            personId = personId,
            placeId = placeId,
            favorites = Favorite.valueOf("WORK")
        )
        whenever(favoritesRepository.save(any<FavoriteEntity>())).thenReturn(favoriteEntity)
        val favoriteRequest = CreateFavoritesRequest(
            personId = personId,
            placeId = placeId,
            favoriteType = "WORK"
        )
        assertEquals(favoriteEntity, favoritesService.addToFavorites(favoriteRequest))
        assertNotNull(favoriteEntity.id)
    }

    @Test
    fun `test saveFavorite throws NotFoundException when place not found`() {
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeService.getPlaceById(placeId)).thenReturn(null)
        whenever(personService.getUserById(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        assertThrows<NotFoundException> {
            favoritesService.addToFavorites(
                CreateFavoritesRequest(
                    personId = UUID.randomUUID().toString(),
                    placeId = UUID.randomUUID().toString(),
                    favoriteType = Favorite.HOME.name
                )
            )
        }
    }

    @Test
    fun `test saveFavorite throws NotFoundException when person not found`() {
        val personId = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        whenever(placeService.getPlaceById(placeId)).thenReturn(Place(placeId, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(personService.getUserById(personId)).thenReturn(null)
        assertThrows<NotFoundException> {
            favoritesService.addToFavorites(
                CreateFavoritesRequest(
                    personId = UUID.randomUUID().toString(),
                    placeId = UUID.randomUUID().toString(),
                    favoriteType = Favorite.HOME.name
                )
            )
        }
    }

    @Test
    fun `test saveFavorite throws IllegalArgumentException when favorite not found`() {
        val personId = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        whenever(placeService.getPlaceById(placeId)).thenReturn(Place(placeId, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(personService.getUserById(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        assertThrows<IllegalArgumentException> {
            favoritesService.addToFavorites(
                CreateFavoritesRequest(
                    personId = personId,
                    placeId = placeId,
                    favoriteType = "ABACABA"
                )
            )
        }
    }

    @Test
    fun `test getFavoriteById returns favorite`() {
        val favoriteId = UUID.randomUUID().toString()
        whenever(favoritesRepository.findById(favoriteId)).thenReturn(Optional.of(mock<FavoriteEntity>()))
        assertDoesNotThrow { favoritesService.getFavoriteById(favoriteId) }
    }

    @Test
    fun `test getFavoriteById throws NotFoundException`() {
        val favoriteId = UUID.randomUUID().toString()
        whenever(favoritesRepository.findById(favoriteId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { favoritesService.getFavoriteById(favoriteId) }
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getFavoritesByPersonId returns list of favorites`(pageSize: Int) {
        val personId = UUID.randomUUID().toString()
        whenever(personService.getUserById(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<MutableList<FavoriteEntity>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(favoritesRepository.findByPersonId(personId, pageable)).thenReturn(mock<Page<FavoriteEntity>>())
        whenever(favoritesRepository.findByPersonId(personId, pageable).content).thenReturn(list)
        val result = favoritesService.getFavoritesByPersonId(personId, 0, pageSize)
        assertEquals(pageSize, result.size)
    }

    @Test
    fun `test getFavoritesByPersonId throws NotFoundException when person not found`() {
        val personId = UUID.randomUUID().toString()
        whenever(personService.getUserById(personId)).thenReturn(null)
        assertThrows<NotFoundException> {
            favoritesService.getFavoritesByPersonId(
                personId,
                page = 0,
                size = 50
            )
        }
    }

    @Test
    fun `test deleteFavorite deletes favorite`() {
        whenever(favoritesRepository.existsById(anyString())).thenReturn(true)
        assertDoesNotThrow { favoritesService.deleteFavorite(UUID.randomUUID().toString()) }
    }

    @Test
    fun `test deleteFavorite throws NotFoundException`() {
        whenever(favoritesRepository.existsById(anyString())).thenReturn(false)
        assertThrows<NotFoundException> { favoritesService.deleteFavorite(UUID.randomUUID().toString()) }
    }
}