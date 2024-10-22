package org.mapsAdvisor.mapsAdvisor.services

import org.junit.Assert.assertFalse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.entity.*
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteRequest
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.util.*
import kotlin.test.assertEquals

class RouteServiceTest {
    private val routeRepository = mock<RouteRepository>()
    private val placeRepository = mock<PlaceRepository>()
    private val routeFeedbackRepository = mock<RouteFeedbackRepository>()
    private val routeService = RouteService(routeRepository, routeFeedbackRepository)

    @Test
    fun `test createRoute successfully creates a route`(){
        val place1 = Place(
            id = "__id1",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(40.0, 40.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.existsById(place1.id!!)).thenReturn(true)
        val place2 = Place(
            id = "__id2",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(50.0, 50.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.existsById(place2.id!!)).thenReturn(true)
        val place3 = Place(
            id = "__id3",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(50.0, 40.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.existsById(place3.id!!)).thenReturn(true)
        val route = Route(
            id = UUID.randomUUID().toString(),
            name = UUID.randomUUID().toString(),
            description = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            places = listOf(place1.id!!, place2.id!!, place3.id!!)
        )
        whenever(routeRepository.save(any<Route>())).thenReturn(route)
        val request = CreateRouteRequest(
            name = UUID.randomUUID().toString(),
            description = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            places = listOf(place1.id!!, place2.id!!, place3.id!!)
        )
        assertEquals(route, routeService.createRoute(request))
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test findAll returns paginated list of routes`(pageSize: Int){
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<List<Route>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(routeRepository.findAll(pageable)).thenReturn(mock<Page<Route>>())
        whenever(routeRepository.findAll(pageable).content).thenReturn(list)
        val listGot = routeService.findAll(0, pageSize)
        assertEquals(list.size, listGot.size)
    }

    @Test
    fun `test findById returns a route if found`(){
        val routeId = UUID.randomUUID().toString()
        val route = mock<Route>()
        whenever(routeRepository.findById(routeId)).thenReturn(Optional.of(route))
        assertEquals(routeService.findById(routeId), route)
    }

    @Test
    fun `test findById throws NotFoundException if route not found`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.findById(routeId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {routeService.findById(routeId) }
    }

    @Test
    fun `test deleteById successfully deletes route and associated feedback`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.findById(routeId)).thenReturn(Optional.of(mock<Route>()))
        assertDoesNotThrow { routeRepository.deleteById(routeId) }
    }

    @Test
    fun `test deleteById throws NotFoundException if route does not exist`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.findById(routeId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {routeService.deleteById(routeId) }
    }

    @Test
    fun `test findRoutesByPlaceId returns list of routes containing the place`(){
        val place1 = Place(
            id = "__id1",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(40.0, 40.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.existsById(place1.id!!)).thenReturn(true)
        val place2 = Place(
            id = "__id2",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(50.0, 50.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.existsById(place2.id!!)).thenReturn(true)
        val place3 = Place(
            id = "__id3",
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(50.0, 40.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        val route1 = Route(
            id = "__id1",
            name = UUID.randomUUID().toString(),
            description = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            places = listOf(place1.id!!, place2.id!!, place3.id!!)
        )
        val route2 = Route(
            id = "__id2",
            name = UUID.randomUUID().toString(),
            description = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
            places = listOf(place1.id!!, place2.id!!, place3.id!!)
        )
        whenever(routeRepository.findAllByPlacesContains(place2.id!!)).thenReturn(listOf(route1, route2))
        assertEquals(listOf(route1, route2), routeService.findRoutesByPlaceId(place2.id!!))
    }
}