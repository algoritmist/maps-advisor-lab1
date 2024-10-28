package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.entity.Route
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.model.request.CreateRouteRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val routeFeedbackRepository: RouteFeedbackRepository,
    private val placeService: PlaceService,
) {
    fun createRoute(request: CreateRouteRequest): Route {
        request.places.forEach { id ->
            placeService.getPlace(id)
                ?: throw NotFoundException("Place with id $id not found")
        }
        return routeRepository.save(
            Route(
                name = request.name,
                description = request.description,
                places = request.places
            )
        )
    }

    fun getAllRoutes(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAll(pageable).content
    }

    fun getRoute(id: String): Route? =
        routeRepository.findByIdOrNull(id)

    @Transactional
    fun deleteRoute(id: String) {
        val routeToDelete = getRoute(id) ?: throw NotFoundException("Route with id $id not found")
        routeRepository.delete(routeToDelete)

        if (routeFeedbackRepository.existsByRouteId(id)) {
            routeFeedbackRepository.deleteAllByRouteId(id)
        }
    }

    fun findRoutesByPlaceContains(placeId: String, page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAllByPlacesContains(placeId, pageable).content
    }
}