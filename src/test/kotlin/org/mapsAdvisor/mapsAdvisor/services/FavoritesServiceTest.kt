package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.entity.Favorite
import org.mapsAdvisor.mapsAdvisor.entity.FavoriteEntity
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals

class FavoritesServiceTest {
    private val favoritesRepository = mock<FavoritesRepository>()
    private val placeRepository = mock<PlaceRepository>()
    private val personRepository = mock<PersonRepository>()

    private val favoritesService = FavoritesService(favoritesRepository, placeRepository, personRepository)

    @Test
    fun `test saveFavorite with valid data`(){}

    @Test
    fun `test saveFavorite throws NotFoundException when place not found`(){
        whenever(placeRepository.existsById(anyString())).thenReturn(false)
        whenever(personRepository.existsById(anyString())).thenReturn(true)
        assertThrows<NotFoundException> { favoritesService.saveFavorite(
            FavoritesRequest(
                personId = UUID.randomUUID().toString(),
                placeId = UUID.randomUUID().toString(),
                favoriteType = Favorite.HOME.name
            )
        ) }
    }

    @Test
    fun `test saveFavorite throws NotFoundException when person not found`(){
        whenever(placeRepository.existsById(anyString())).thenReturn(true)
        whenever(personRepository.existsById(anyString())).thenReturn(false)
        assertThrows<NotFoundException> { favoritesService.saveFavorite(
            FavoritesRequest(
                personId = UUID.randomUUID().toString(),
                placeId = UUID.randomUUID().toString(),
                favoriteType = Favorite.HOME.name
            )
        ) }
    }

    @Test
    fun `test getFavoriteById returns favorite`(){
        val favoriteId = UUID.randomUUID().toString()
        whenever(favoritesRepository.findById(favoriteId)).thenReturn(Optional.of(mock<FavoriteEntity>()))
        assertDoesNotThrow { favoritesService.getFavoriteById(favoriteId) }
    }

    @Test
    fun `test getFavoriteById throws NotFoundException`(){
        val favoriteId = UUID.randomUUID().toString()
        whenever(favoritesRepository.findById(favoriteId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { favoritesService.getFavoriteById(favoriteId) }
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getFavoritesByPersonId returns list of favorites`(pageSize: Int){
        val personId = UUID.randomUUID().toString()
        whenever(personRepository.existsById(personId)).thenReturn(true)
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<MutableList<FavoriteEntity>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(favoritesRepository.findByPersonId(personId, pageable)).thenReturn(mock<Page<FavoriteEntity>>())
        whenever(favoritesRepository.findByPersonId(personId, pageable).content).thenReturn(list)
        val result = favoritesService.getFavoritesByPersonId(personId, 0, pageSize)
        assertEquals(pageSize, result.size)
    }

    @Test
    fun `test getFavoritesByPersonId throws NotFoundException when person not found`(){
        whenever(personRepository.findById(anyString())).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { favoritesService.getFavoritesByPersonId(
            UUID.randomUUID().toString(),
            page = 0,
            size = 50
        ) }
    }

    @Test
    fun `test deleteFavorite deletes favorite`(){
        whenever(favoritesRepository.existsById(anyString())).thenReturn(true)
        assertDoesNotThrow { favoritesService.deleteFavorite(UUID.randomUUID().toString()) }
    }

    @Test
    fun `test deleteFavorite throws NotFoundException`(){
        whenever(favoritesRepository.existsById(anyString())).thenReturn(false)
        assertThrows<NotFoundException> { favoritesService.deleteFavorite(UUID.randomUUID().toString())}
    }
}