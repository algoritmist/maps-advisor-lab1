package org.mapsAdvisor.mapsAdvisor

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mapsAdvisor.mapsAdvisor.entity.Favorite
import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.entity.Role
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
@SpringBootTest
class FavoritesServiceTest {

    companion object {
        @Autowired
        private lateinit var favoritesService: FavoritesService

        @Autowired
        private lateinit var personService: PersonService

        @Autowired
        private lateinit var placeService: PlaceService

        private lateinit var baeldung: Person
        private lateinit var itmo: Place

        private val baeldungRequest = PersonRequest(
            name = "eugene",
            username = "baeldung",
            password = "baeldung",
            role = Role.USER.name,
            placesOwned = listOf()
        )

        private val itmoRequest = PlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9563, 30.31),
            tags = listOf("university"),
            owners = listOf(),
            info = "The best university in the world:)"
        )

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            baeldung = personService.createPerson(baeldungRequest)
            itmo = placeService.createPlace(itmoRequest)
        }
    }

    @Test
    fun saveFavorite() {
        val favoriteRequest = FavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "WORK"
        )
        val favorite = favoritesService.saveFavorite(favoriteRequest)
        assertEquals(favoriteRequest.personId, favorite.personId)
        assertEquals(favoriteRequest.placeId, favorite.placeId)
        assertEquals(Favorite.WORK, favorite.favorites)
    }

    @Test
    fun saveFavouriteWrongType() {
        val favoriteRequest = FavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun saveFavouriteWrongPersonId() {
        val favoriteRequest = FavoritesRequest(
            personId = "idPerson404",
            placeId = itmo.id!!,
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun saveFavouriteWrongPlaceId() {
        val favoriteRequest = FavoritesRequest(
            personId = baeldung.id!!,
            placeId = "idPlace404",
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun getFavoriteById() {
        val favoriteRequest = FavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "WORK"
        )
        val favorite = favoritesService.saveFavorite(favoriteRequest)
        val favoriteFound = favoritesService.getFavoriteById(favorite.id!!)
        assertEquals(favorite, favoriteFound)
    }

    @Test
    fun getFavoriteByWrongId() {
        assertThrows<NotFoundException> { favoritesService.getFavoriteById("idFavorite404") }
    }

    @Test
    fun getFavoritesByPersonId() {

    }

    @Test
    fun getFavoritesByWrongPersonId() {
        assertThrows<NotFoundException> { favoritesService.getFavoritesByPersonId("idPerson04", 50, 40) }
    }

    @Test
    fun deleteFavorite() {
        val favoriteRequest = FavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "WORK"
        )
        val favorite = favoritesService.saveFavorite(favoriteRequest)
        assertDoesNotThrow { favoritesService.deleteFavorite(favorite.id!!) }
    }

    @Test
    fun deleteFavoriteWrongId(){
        assertThrows<NotFoundException> { favoritesService.deleteFavorite("idFavorite404")  }
    }
}