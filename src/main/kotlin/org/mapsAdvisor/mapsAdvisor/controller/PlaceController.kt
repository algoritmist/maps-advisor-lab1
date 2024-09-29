package org.mapsAdvisor.mapsAdvisor.controller

import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.response.PlaceResponse
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/place")
class CompanyController(
    private val placeService: PlaceService
) {

    @PostMapping
    fun createPlace(@RequestBody request: PlaceRequest): ResponseEntity<PlaceResponse> {
        val createdPlace = placeService.createPlace(request)

        return ResponseEntity
            .ok(
                PlaceResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping
    fun findAllPlaces(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findAll(page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @GetMapping("/{id}")
    fun findPlaceById(
        @PathVariable id: String,
    ): ResponseEntity<PlaceResponse> {
        val company = placeService.findById(id)

        return ResponseEntity
            .ok(
                PlaceResponse.fromEntity(company)
            )
    }

    @GetMapping
    fun findByLocationNear(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findByLocationNear(latitude, longitude, distanceKm, page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @GetMapping
    fun findNearbyPlacesWithTag(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam tag: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesWithTag(latitude, longitude, distanceKm, tag, page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @GetMapping
    fun findNearbyPlacesByName(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam name: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesByName(latitude, longitude, distanceKm, name, page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @DeleteMapping("/{id}")
    fun deletePlace(@PathVariable id: String): ResponseEntity<Void> {
        placeService.deleteById(id)

        return ResponseEntity.noContent().build()
    }
}