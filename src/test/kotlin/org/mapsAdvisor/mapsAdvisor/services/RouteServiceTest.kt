package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.test.assertEquals

class RouteServiceTest {
    private val routeRepository = mock<RouteRepository>()
    private val routeFeedbackRepository = mock<RouteFeedbackRepository>()
    private val routeService = RouteService(routeRepository, routeFeedbackRepository)

    @Test
    fun `test createRoute successfully creates a route`(){}

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
    fun `test deleteById successfully deletes route and associated feedback`(){}

    @Test
    fun `test deleteById throws NotFoundException if route does not exist`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.findById(routeId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {routeService.deleteById(routeId) }
    }

    @Test
    fun `test findRoutesByPlaceId returns list of routes containing the place`(){}
}