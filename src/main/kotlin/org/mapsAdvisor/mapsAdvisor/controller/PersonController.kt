package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.mapsAdvisor.mapsAdvisor.response.PersonResponse
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mapsAdvisor.mapsAdvisor.service.PersonWithPlacesResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/auth", produces = [MediaType.APPLICATION_JSON_VALUE])
class PersonController(private val userService: PersonService) {
    @PostMapping("/signup")
    fun createUser(@Valid @RequestBody newUser: PersonRequest): ResponseEntity<PersonResponse> {
        val person = userService.createPerson(newUser)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PersonResponse.fromEntity(person)
            )
    }

    @PostMapping("/assign")
    fun assignPlaceToUser(
        @RequestParam personId: String,
        @RequestParam placeId: String
    ): ResponseEntity<PersonWithPlacesResponse> {
        return try {
            val updatedPerson = userService.assignPlaceToUser(personId, placeId)
            ResponseEntity.ok(updatedPerson)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }
}