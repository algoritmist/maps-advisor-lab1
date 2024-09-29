package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.model.Route
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.request.RouteRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service

@Service
class RouteService(
    private val routeRepository: RouteRepository
) {
    fun createRoute(request: RouteRequest): Route =
        routeRepository.save(
            Route(
                name = request.name,
                description = request.description,
                places = request.places
            )
        )

    fun findAll(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAll(pageable).content
    }

    fun findById(id: String): Route =
        routeRepository.findById(id)
            .orElseThrow { NotFoundException("Route with id $id not found") }
}