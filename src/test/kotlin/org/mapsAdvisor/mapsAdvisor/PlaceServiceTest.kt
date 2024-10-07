package org.mapsAdvisor.mapsAdvisor

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.junit.runner.RunWith
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest
class PlaceServiceTest {
    @Autowired
    private lateinit var placeService: PlaceService

    private val itmo = PlaceRequest(
        name = "ITMO University",
        coordinates = Coordinates(59.9572, 30.3083),
        tags = listOf(),
        owners = listOf(),
        info = "The best university in the world:)"
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
        val place = PlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9572, 30.3083),
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
}