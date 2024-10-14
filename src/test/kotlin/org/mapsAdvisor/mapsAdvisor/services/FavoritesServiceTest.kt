package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapsAdvisor.mapsAdvisor.entity.Favorite
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.*

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

    }

    @Test
    fun `test getFavoriteById throws NotFoundException`(){
        whenever(favoritesRepository.findById(anyString())).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { favoritesRepository.existsById(UUID.randomUUID().toString()) }
    }

    @Test
    fun `test getFavoritesByPersonId returns list of favorites`(){}

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
    fun `test deleteFavorite calls repository delete`(){}

    @Test
    fun `test deleteFavorite throws NotFoundException`(){
        whenever(favoritesRepository.existsById(anyString())).thenReturn(false)
        assertThrows<NotFoundException> { favoritesService.deleteFavorite(UUID.randomUUID().toString())}
    }
}