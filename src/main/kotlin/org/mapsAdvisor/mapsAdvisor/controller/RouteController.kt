package org.mapsAdvisor.mapsAdvisor.controller

import org.mapsAdvisor.mapsAdvisor.request.RouteRequest
import org.mapsAdvisor.mapsAdvisor.response.RouteResponse
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/route")
class RouteController(
    private val routeService: RouteService
) {

    @PostMapping
    fun createRoute(@RequestBody request: RouteRequest): ResponseEntity<RouteResponse> {
        val createdPlace = routeService.createRoute(request)

        return ResponseEntity
            .ok(
                RouteResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping
    fun findAllRoutes(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
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

    @GetMapping("/by-place/{placeId}")
    fun findRoutesByPlaceId(@PathVariable placeId: String): ResponseEntity<List<RouteResponse>> {
        val routes = routeService.findRoutesByPlaceId(placeId)
        return ResponseEntity.ok(routes.map { RouteResponse.fromEntity(it) })
    }
}