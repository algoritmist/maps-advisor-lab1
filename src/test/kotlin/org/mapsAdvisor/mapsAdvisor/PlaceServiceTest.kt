package org.mapsAdvisor.mapsAdvisor

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.runner.RunWith
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertContains
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest
class PlaceServiceTest {
    /*@Autowired
    private lateinit var placeService: PlaceService

    private val itmo = CreatePlaceRequest(
        name = "ITMO University",
        coordinates = Coordinates(59.9563, 30.31),
        tags = listOf("university"),
        owners = listOf(),
        info = "The best university in the world:)"
    )

    private val volcheka = CreatePlaceRequest(
        name = "Volcheka Bulochnaya",
        coordinates = Coordinates(59.9576, 30.3075),
        tags = listOf("backery"),
        owners = listOf(),
        info = "Volchecka backery"
    )

    private val hermitage = CreatePlaceRequest(
        name = "Hermitage museum",
        coordinates = Coordinates(59.939864, 59.939864),
        tags = listOf("museum"),
        owners = listOf(),
        info = "The famous museum of St. Petersburg"
    )

    @Test
    fun createPlace(){
        val place = placeService.createPlace(itmo)
        assertNotNull(place)
        assertEquals(place.name, itmo.name)
        assertEquals(place.coordinates, itmo.coordinates)
        assertTrue(place.tags.isEmpty())
        assertTrue(place.owners.isEmpty())
        assertEquals(place.info, itmo.info)
    }

    @Test
    fun createExistingPlace(){
        createPlace()
        assertThrows<IllegalArgumentException> { createPlace() }
    }

    @Test
    fun createIncompleteOwners(){
        createPlace()
        val place = CreatePlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9563, 30.31),
            tags = listOf(),
            owners = listOf("id404"),
            info = "The best university in the world:)"
        )
        assertThrows<NotFoundException> { placeService.createPlace(place)}
    }

    // TODO: fill the database
    @ParameterizedTest
    @CsvSource(value = [
        "10, 200",
        "20, 100",
        "50, 40"
    ])
    fun findAll(page: Int, pageSize: Int) {
        val content = placeService.findAll(page, pageSize)
        assertTrue(content.size <= page * pageSize)
    }

    @Test
    fun findById(){
        val place = placeService.createPlace(itmo)
        val foundPlace = placeService.findById(place.id!!)
        assertEquals(place, foundPlace)
    }

    @Test
    fun findByWrongId(){
        assertThrows<NotFoundException> { placeService.findById("idPlace404") }
    }

    @Test
    fun findByLocationNear(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        placeService.createPlace(hermitage)
        val places = placeService.findByLocationNear(30.30, 59.95, 1.0, 50, 40)
        assertEquals(2, places.size)
        assertContains(places, itmoPlace)
        assertContains(places, volchekaPlace)
    }

    @Test
    fun findNearbyPlacesWithTag(){
        val itmoPlace = placeService.createPlace(itmo)
        placeService.createPlace(volcheka)
        placeService.createPlace(hermitage)
        val places = placeService.findNearbyPlacesWithTag(30.30, 59.95, 1.0, "university", 50, 40)
        assertEquals(1, places.size)
        assertContains(places, itmoPlace)
    }
    @Test
    fun findNearbyPlacesByName(){
        placeService.createPlace(itmo)
        placeService.createPlace(volcheka)
        val hermitagePlace = placeService.createPlace(hermitage)
        val places = placeService.findNearbyPlacesByName(30.30, 59.95, 10.0, "Hermitage", 50, 40)
        assertEquals(1, places.size)
        assertContains(places, hermitagePlace)
    }

    @Test
    fun deleteById(){
        val itmoPlace = placeService.createPlace(itmo)
        assertDoesNotThrow { placeService.deleteById(itmoPlace.id!!) }
    }

    @Test
    fun deleteByWrongId(){
        assertThrows<NotFoundException> { placeService.deleteById("idPlace404") }
    }

    @Test
    fun countAll(){
        placeService.createPlace(itmo)
        placeService.createPlace(volcheka)
        placeService.createPlace(hermitage)
        //assertEquals(3, placeService.countAll())
    }*/
}