package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import org.mapsAdvisor.mapsAdvisor.controller.RouteController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.request.CreateRouteRequest
import org.mapsAdvisor.mapsAdvisor.model.response.RouteResponse
import org.mapsAdvisor.mapsAdvisor.service.MAX_PAGE_SIZE
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class RouteController(
    private val routeService: RouteService
) {

    @PostMapping
    fun createRoute(@Valid @RequestBody request: CreateRouteRequest): ResponseEntity<RouteResponse> {
        val createdPlace = routeService.createRoute(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                RouteResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: String): ResponseEntity<RouteResponse> {
        val route =
            routeService.getRoute(id) ?: throw NotFoundException("Route with id $id not found")
        return ResponseEntity
            .ok(
                RouteResponse.fromEntity(route)
            )
    }

    @GetMapping
    fun getAllRoutes(
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<RouteResponse>> {
        val routes = routeService.getAllRoutes(page, size)

        return ResponseEntity
            .ok(
                routes.map { RouteResponse.fromEntity(it) }
            )
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: String): ResponseEntity<Void> {
        routeService.deleteRoute(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-place/{id}")
    fun getRoutesByPlaceContains(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<RouteResponse>> {
        val routes = routeService.findRoutesByPlaceContains(id, page, size)
        return ResponseEntity.ok(routes.map { RouteResponse.fromEntity(it) })
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/route"
    }
}