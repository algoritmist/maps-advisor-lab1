package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.response.PlaceResponse
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/place")
class PlaceController(
    private val placeService: PlaceService
) {

    @PostMapping
    fun createPlace(@Valid @RequestBody request: PlaceRequest): ResponseEntity<PlaceResponse> {
        val createdPlace = placeService.createPlace(request)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PlaceResponse.fromEntity(createdPlace)
            )
    }

    @GetMapping
    fun findAllPlaces(
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val places = placeService.findAll(page)

        return ResponseEntity
            .ok(
                places.map { PlaceResponse.fromEntity(it) }
            )
    }

    @GetMapping("/{id}")
    fun findPlaceById(
        @NotBlank @PathVariable id: String,
    ): ResponseEntity<PlaceResponse> {
        return try {
            val place = placeService.findById(id)
            ResponseEntity.ok(PlaceResponse.fromEntity(place))  // 200 OK
        } catch (e: NotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()  // 404 Not Found
        }
    }

    @GetMapping("/near")
    fun findByLocationNear(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") latitude: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val places = placeService.findByLocationNear(latitude, longitude, distanceKm, page)
        val totalPlaces = placeService.countAll()

        return ResponseEntity
            .ok()
            .header("X-Total-Count", totalPlaces.toString())
            .body(places.map { PlaceResponse.fromEntity(it) })
    }

    @GetMapping("/tag")
    fun findNearbyPlacesWithTag(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") latitude: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") longitude: Double,
        @RequestParam(required = false, defaultValue = "5.0") distanceKm: Double,
        @RequestParam tag: String,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesWithTag(latitude, longitude, distanceKm, tag, page)

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
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<List<PlaceResponse>> {
        val companies = placeService.findNearbyPlacesByName(latitude, longitude, distanceKm, name, page)

        return ResponseEntity
            .ok(
                companies.map { PlaceResponse.fromEntity(it) }
            )
    }

    @DeleteMapping("/{id}")
    fun deletePlace(@NotBlank @PathVariable id: String): ResponseEntity<Void> {
        return try {
            placeService.deleteById(id)
            ResponseEntity.noContent().build()
        } catch (e: NotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}