package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteRepository
import org.mapsAdvisor.mapsAdvisor.request.RouteRequest
import org.springframework.dao.DataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RouteService(
    private val routeRepository: RouteRepository,
    private val routeFeedbackRepository: RouteFeedbackRepository
) {
    fun createRoute(request: RouteRequest): Route {
        return try {
            routeRepository.save(
                Route(
                    name = request.name,
                    description = request.description,
                    places = request.places
                )
            )
        } catch (ex: DataAccessException) {
            throw IllegalStateException("Failed to create route due to a database error", ex)
        }
    }

    fun findAll(page: Int, size: Int): List<Route> {
        val pageable = PageRequest.of(page, size)
        return routeRepository.findAll(pageable).content
    }

    fun findById(id: String): Route =
        routeRepository.findById(id)
            .orElseThrow { NotFoundException("Route with id $id not found") }

    @Transactional
    fun deleteById(id: String) {
        try {
            val routeToDelete = findById(id)

            routeRepository.delete(routeToDelete)

            routeFeedbackRepository.deleteAllByRouteId(id)
        } catch (ex: NotFoundException) {
            throw ex
        } catch (ex: DataAccessException) {
            throw IllegalStateException("Failed to delete routes or associated records", ex)
        }
    }

    fun findRoutesByPlaceId(placeId: String): List<Route> {
        return routeRepository.findAllByPlacesContains(placeId)
    }
}