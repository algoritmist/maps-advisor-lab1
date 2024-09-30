package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.request.PersonLoginRequest
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.mapsAdvisor.mapsAdvisor.request.PersonSignupRequest
import org.mapsAdvisor.mapsAdvisor.response.PersonResponse
import org.mapsAdvisor.mapsAdvisor.response.PlaceResponse
import org.mapsAdvisor.mapsAdvisor.service.PersonService
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
}