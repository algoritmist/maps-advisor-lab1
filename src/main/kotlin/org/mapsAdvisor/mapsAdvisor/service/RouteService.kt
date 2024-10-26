package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.entity.Route
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.model.request.CreateRouteRequest
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val routeFeedbackRepository: RouteFeedbackRepository
) {
    fun createRoute(request: CreateRouteRequest): Route {
        return routeRepository.save(
            Route(
                name = request.name,
                description = request.description,
                places = request.places
            )
        )
    }

    fun findAll(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAll(pageable).content
    }

    fun getRouteById(id: String): Route? =
        routeRepository.findByIdOrNull(id)

    @Transactional
    fun deleteById(id: String) {
        val routeToDelete = getRouteById(id) ?: throw NotFoundException("Route with id $id not found")
        routeRepository.delete(routeToDelete)

        if (routeFeedbackRepository.existsByRouteId(id)) {
            routeFeedbackRepository.deleteAllByRouteId(id)
        }
    }

    fun findRoutesByPlaceId(placeId: String, page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAllByPlacesContains(placeId, pageable).content
    }
}