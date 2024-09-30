package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.Place
import org.mapsAdvisor.mapsAdvisor.model.Route
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.request.RouteRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val routeFeedbackRepository: RouteFeedbackRepository
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

    @Transactional
    fun deleteById(id: String) {
        val placeToDelete = findById(id)

        routeRepository.delete(placeToDelete)

        routeFeedbackRepository.deleteAllByRouteId(id)
    }


}