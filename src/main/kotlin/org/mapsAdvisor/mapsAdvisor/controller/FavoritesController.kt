package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import org.mapsAdvisor.mapsAdvisor.controller.FavoritesController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.request.CreateFavoritesRequest
import org.mapsAdvisor.mapsAdvisor.response.FavoritesResponse
import org.mapsAdvisor.mapsAdvisor.service.FavoritesService
import org.mapsAdvisor.mapsAdvisor.service.MAX_PAGE_SIZE
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class FavoritesController(
    private val favoritesService: FavoritesService
) {
    @PostMapping
    fun createFavorite(@Valid @RequestBody favoriteEntity: CreateFavoritesRequest): ResponseEntity<FavoritesResponse> {
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


    @GetMapping("/person/{id}")
    fun getFavoritesByPersonId(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int,
    ): ResponseEntity<List<FavoritesResponse>> {
        val favorites = favoritesService.getFavoritesByPersonId(id, page, size)
        return ResponseEntity.ok(
            favorites.map { FavoritesResponse.fromEntity(it) }
        )
    }

    @DeleteMapping("/{id}")
    fun deleteFavorite(@PathVariable id: String): ResponseEntity<Unit> {
        favoritesService.deleteFavorite(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/favorites"
    }
}