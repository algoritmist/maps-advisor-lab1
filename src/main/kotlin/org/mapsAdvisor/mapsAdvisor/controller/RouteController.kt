package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import org.mapsAdvisor.mapsAdvisor.controller.RouteController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteRequest
import org.mapsAdvisor.mapsAdvisor.response.RouteResponse
import org.mapsAdvisor.mapsAdvisor.service.MAX_PAGE_SIZE
import org.mapsAdvisor.mapsAdvisor.service.RouteService
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
    fun createRoute(@RequestBody request: CreateRouteRequest): ResponseEntity<RouteResponse> {
        val createdPlace = routeService.createRoute(request)

        return ResponseEntity
            .ok(
                RouteResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping
    fun findAllRoutes(
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<RouteResponse>> {
        val companies = routeService.findAll(page, size)

        return ResponseEntity
            .ok(
                companies.map { RouteResponse.fromEntity(it) }
            )
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: String): ResponseEntity<Void> {
        routeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/by-place/{id}")
    fun findRoutesByPlaceId(@PathVariable id: String): ResponseEntity<List<RouteResponse>> {
        val routes = routeService.findRoutesByPlaceId(id)
        return ResponseEntity.ok(routes.map { RouteResponse.fromEntity(it) })
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/route"
    }
}