package org.mapsAdvisor.mapsAdvisor.integration.repo

import org.assertj.core.api.Assertions.assertThat
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.integration.IntegrationEnvironment
import org.mapsAdvisor.mapsAdvisor.model.entity.Route
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class RouteRepositoryIntegrationTest : IntegrationEnvironment() {
    @Autowired
    private lateinit var routeRepository: RouteRepository

    @Test
    fun givenRouteRepository_whenFindAllWithPagination_thenOK() {
        val route1 = Route(id = "201", name = "Route 1", description = "the best", places = listOf("place1", "place2"))
        val route2 = Route(id = "202", name = "Route 2", description = "the best", places = listOf("place3"))
        routeRepository.saveAll(listOf(route1, route2))

        val pageable: Pageable = PageRequest.of(0, 50)
        val routes = routeRepository.findAll(pageable)

        assertThat(routes.content.size).isGreaterThanOrEqualTo(2)
        assertThat(routes.content).contains(route1, route2)
    }

    @Test
    fun givenRouteRepository_whenFindAllByPlacesContains_thenOK() {
        val route1 = Route(id = "203", name = "Route 3", description = "the best", places = listOf("place1", "place4"))
        val route2 = Route(id = "204", name = "Route 4", description = "the best", places = listOf("place1", "place5"))
        val route3 = Route(id = "205", name = "Route 5", description = "the best", places = listOf("place2", "place8"))
        routeRepository.saveAll(listOf(route1, route2, route3))

        val pageable: Pageable = PageRequest.of(0, 50)
        val foundRoutes = routeRepository.findAllByPlacesContains("place1", pageable)

        assertThat(foundRoutes).isNotEmpty
        assertThat(foundRoutes).contains(route1, route2)
    }

    @Test
    fun givenRouteRepository_whenDeleteRoute_thenNotFound() {
        val route = Route(id = "205", name = "Route to Delete", description = "tes best", places = listOf("place6", "place7"))
        val savedRoute: Route = routeRepository.save(route)

        routeRepository.delete(savedRoute)

        val deletedRoute = routeRepository.findById(savedRoute.id!!)
        assertThat(deletedRoute.isPresent).isFalse
    }

}