package org.mapsAdvisor.mapsAdvisor

import junit.framework.TestCase.assertTrue
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
import org.mapsAdvisor.mapsAdvisor.request.CreateFavoritesRequest
import org.mapsAdvisor.mapsAdvisor.request.CreatePersonRequest
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertContains
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
@SpringBootTest
class FavoritesServiceTest {
/*
    companion object {
        @Autowired
        private lateinit var favoritesService: FavoritesService

        @Autowired
        private lateinit var personService: PersonService

        @Autowired
        private lateinit var placeService: PlaceService

        private lateinit var baeldung: Person
        private lateinit var tsopa: Person

        private lateinit var itmo: Place
        private lateinit var hermitage: Place

        private val baeldungRequest = CreatePersonRequest(
            name = "eugene",
            username = "baeldung",
            password = "baeldung",
            role = Role.USER.name,
            placesOwned = listOf()
        )

        private val tsopaRequest = CreatePersonRequest(
            name = "tsopa",
            username = "tsopa",
            password = "tsopa",
            role = Role.USER.name,
            placesOwned = listOf()
        )

        private val itmoRequest = CreatePlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9563, 30.31),
            tags = listOf("university"),
            owners = listOf(),
            info = "The best university in the world:)"
        )

        private val hermitageRequest = CreatePlaceRequest(
            name = "Hermitage museum",
            coordinates = Coordinates(59.939864, 59.939864),
            tags = listOf("museum"),
            owners = listOf(),
            info = "The famous museum of St. Petersburg"
        )

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            baeldung = personService.createPerson(baeldungRequest)
            tsopa = personService.createPerson(tsopaRequest)
            itmo = placeService.createPlace(itmoRequest)
            hermitage = placeService.createPlace(hermitageRequest)
        }
    }

    @Test
    fun saveFavorite() {
        val favoriteRequest = CreateFavoritesRequest(
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
        val favoriteRequest = CreateFavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun saveFavouriteWrongPersonId() {
        val favoriteRequest = CreateFavoritesRequest(
            personId = "idPerson404",
            placeId = itmo.id!!,
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun saveFavouriteWrongPlaceId() {
        val favoriteRequest = CreateFavoritesRequest(
            personId = baeldung.id!!,
            placeId = "idPlace404",
            favoriteType = "CHILL"
        )
        assertThrows<IllegalArgumentException> { favoritesService.saveFavorite(favoriteRequest) }
    }

    @Test
    fun getFavoriteById() {
        val favoriteRequest = CreateFavoritesRequest(
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
        val workRequest = CreateFavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "WORK"
        )

        val entertainmentRequest = CreateFavoritesRequest(
            personId = baeldung.id!!,
            placeId = itmo.id!!,
            favoriteType = "ENTERTAINMENT"
        )
        val work = favoritesService.saveFavorite(workRequest)
        val entertainment = favoritesService.saveFavorite(entertainmentRequest)
        val favorites = favoritesService.getFavoritesByPersonId(baeldung.id!!, 50, 40)
        assertEquals(2, favorites.size)
        assertContains(favorites, work)
        assertContains(favorites, entertainment)

        val tsopaFavorites = favoritesService.getFavoritesByPersonId(tsopa.id!!, 50, 40)
        assertTrue(tsopaFavorites.isEmpty())
    }

    @Test
    fun getFavoritesByWrongPersonId() {
        assertThrows<NotFoundException> { favoritesService.getFavoritesByPersonId("idPerson04", 50, 40) }
    }

    @Test
    fun deleteFavorite() {
        val favoriteRequest = CreateFavoritesRequest(
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
    }*/
}