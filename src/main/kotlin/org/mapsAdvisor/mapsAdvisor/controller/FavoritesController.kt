package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.request.FavoritesRequest
import org.mapsAdvisor.mapsAdvisor.response.FavoritesResponse
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/favorites")
class FavoritesController(
    private val favoritesService: FavoritesService
) {
    @PostMapping
    fun createFavorite(@Valid @RequestBody favoriteEntity: FavoritesRequest): ResponseEntity<FavoritesResponse> {
        val savedFavorite = favoritesService.saveFavorite(favoriteEntity)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                FavoritesResponse.fromEntity(savedFavorite)
            )
    }

    @GetMapping("/{id}")
    fun getFavoriteById(@PathVariable id: String): ResponseEntity<FavoritesResponse> {
        val favorite = favoritesService.getFavoriteById(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                FavoritesResponse.fromEntity(favorite)
            )
    }


    @GetMapping("/person/{personId}")
    fun getFavoritesByPersonId(
        @PathVariable personId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<List<FavoritesResponse>> {
        val favorites = favoritesService.getFavoritesByPersonId(personId, page, size)
        return ResponseEntity.ok(
            favorites.map { FavoritesResponse.fromEntity(it) }
        )
    }

    @DeleteMapping("/{id}")
    fun deleteFavorite(@PathVariable id: String): ResponseEntity<Void> {
        favoritesService.deleteFavorite(id)
        return ResponseEntity.noContent().build()
    }
}