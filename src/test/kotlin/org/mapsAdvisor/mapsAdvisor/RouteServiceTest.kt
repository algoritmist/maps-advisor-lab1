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
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteRequest
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertContains

@RunWith(SpringRunner::class)
@SpringBootTest
class RouteServiceTest {
    /*@Autowired
    private lateinit var routeService: RouteService

    @Autowired
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
        coordinates = Coordinates(59.9398, 30.3145),
        tags = listOf("museum"),
        owners = listOf(),
        info = "The famous museum of St. Petersburg"
    )

    private val isakievsky = CreatePlaceRequest(
        name = "Isakievsky Sobor",
        coordinates = GeoJsonPoint(59.93391, 30.3064),
    )

    @Test
    fun createRoute(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        val hermitagePlace = placeService.createPlace(hermitage)

        val CreateRouteRequest = CreateRouteRequest(
            name = "ITMO - Backery - Hermitage",
            description = "Just a route from ITMO to Hermitage",
            places = mutableListOf(itmoPlace.id!!, volchekaPlace.id!!, hermitagePlace.id!!)
        )

        val route = routeService.createRoute(CreateRouteRequest)
        assertEquals(CreateRouteRequest.name, route.name)
        assertEquals(CreateRouteRequest.description, route.description)
        assertEquals(CreateRouteRequest.places, route.places)
    }

    @Test
    fun createRouteWrongPlace(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        val CreateRouteRequest = CreateRouteRequest(
            name = "ITMO - Backery - ?",
            description = "Just a route from ITMO to ?",
            places = mutableListOf(itmoPlace.id!!, volchekaPlace.id!!, "idPlace404")
        )
        assertThrows<NotFoundException> { routeService.createRoute(CreateRouteRequest) }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "10, 200",
        "20, 100",
        "50, 40"
    ])

    // TODO: fill the database
    fun findAll(page: Int, pageSize: Int) {
        val content = routeService.findAll(page, pageSize)
        assertTrue(content.size <= page * pageSize)
    }

    @Test
    fun findById(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        val hermitagePlace = placeService.createPlace(hermitage)
        val CreateRouteRequest = CreateRouteRequest(
            name = "ITMO - Backery - Hermitage",
            description = "Just a route from ITMO to Hermitage",
            places = mutableListOf(itmoPlace.id!!, volchekaPlace.id!!, hermitagePlace.id!!)
        )
        val route = routeService.createRoute(CreateRouteRequest)
        val foundRoute = placeService.findById(route.id!!)
        assertEquals(route, foundRoute)
    }

    @Test
    fun findByWrongId(){
        assertThrows<NotFoundException> { routeService.findById("idRoute404") }
    }

    @Test
    fun deleteById(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        val hermitagePlace = placeService.createPlace(hermitage)

        val CreateRouteRequest = CreateRouteRequest(
            name = "ITMO - Backery - Hermitage",
            description = "Just a route from ITMO to Hermitage",
            places = mutableListOf(itmoPlace.id!!, volchekaPlace.id!!, hermitagePlace.id!!)
        )

        val route = routeService.createRoute(CreateRouteRequest)
        assertDoesNotThrow { routeService.deleteById(route.id!!) }
    }

    @Test
    fun deleteByWrongId(){
        assertThrows<NotFoundException> {routeService.deleteById("idRoute404")  }
    }

    @Test
    fun findRoutesByPlaceId(){
        val itmoPlace = placeService.createPlace(itmo)
        val volchekaPlace = placeService.createPlace(volcheka)
        val hermitagePlace = placeService.createPlace(hermitage)
        val isakievskyPlace = placeService.createPlace(isakievsky)

        val firstRoute = routeService.createRoute(CreateRouteRequest(
            name = "ITMO - Backery - Hermitage",
            description = "Just a route from ITMO to Hermitage",
            places = mutableListOf(itmoPlace.id!!, volchekaPlace.id!!, hermitagePlace.id!!)
        ))
        val secondRoute = routeService.createRoute(CreateRouteRequest(
            name = "Isakievsky - Backery - Hermitage",
            description = "Just a route from Isakievsky to Hermitage",
            places = mutableListOf(isakievskyPlace.id!!, volchekaPlace.id!!, hermitagePlace.id!!)
        ))
        val routes = routeService.findRoutesByPlaceId(hermitagePlace.id!!)
        assertEquals(2, routes.size)
        assertContains(routes, firstRoute)
        assertContains(routes, secondRoute)
    }*/
}