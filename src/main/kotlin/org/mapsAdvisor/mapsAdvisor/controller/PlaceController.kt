package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import org.mapsAdvisor.mapsAdvisor.controller.PlaceController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.response.PlaceResponse
import org.mapsAdvisor.mapsAdvisor.service.MAX_PAGE_SIZE
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class PlaceController(
    private val placeService: PlaceService
) {

    @PostMapping
    fun createPlace(@Valid @RequestBody createPlaceRequest: CreatePlaceRequest): ResponseEntity<PlaceResponse> {
        val createdPlace = placeService.createPlace(createPlaceRequest)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PlaceResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping
    fun findAllPlaces(
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val places = placeService.findAll(page, size)
        val totalCount = places.size.toLong()
        val headers = preparePagingHeaders(totalCount, page, size)

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(places.map { PlaceResponse.fromEntity(it) })
    }

    @GetMapping("/{id}")
    fun findPlaceById(@PathVariable id: String): ResponseEntity<PlaceResponse> {
        val place = placeService.findById(id)
        return ResponseEntity
            .ok(
                PlaceResponse.fromEntity(place)
            )
    }

    @GetMapping("/near")
    fun findByLocationNear(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") latitude: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val places = placeService.findByLocationNear(latitude, longitude, distanceKm, page, size)
        val totalCount = places.size.toLong()
        val headers = preparePagingHeaders(totalCount, page, size)

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(places.map { PlaceResponse.fromEntity(it) })
    }

    @GetMapping("/tag")
    fun findNearbyPlacesWithTag(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") latitude: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam tag: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesWithTag(latitude, longitude, distanceKm, tag, page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @GetMapping("/name")
    fun findNearbyPlacesByName(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") latitude: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam name: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesByName(latitude, longitude, distanceKm, name, page, size)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @DeleteMapping("/{id}")
    fun deletePlace(@PathVariable id: String): ResponseEntity<Unit> {
        placeService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/place"
    }
}