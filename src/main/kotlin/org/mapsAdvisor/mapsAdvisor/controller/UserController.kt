package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.controller.UserController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.request.AssignPlaceToPersonRequest
import org.mapsAdvisor.mapsAdvisor.request.CreatePersonRequest
import org.mapsAdvisor.mapsAdvisor.response.PersonResponse
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mapsAdvisor.mapsAdvisor.response.PersonWithPlacesResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class UserController(
    private val userService: PersonService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<PersonResponse> {
        val person = userService.getPerson(id)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                PersonResponse.fromEntity(person)
            )
    }

    @PostMapping("/signup")
    fun createUser(@Valid @RequestBody newUser: CreatePersonRequest): ResponseEntity<PersonResponse> {
        val person = userService.createPerson(newUser)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PersonResponse.fromEntity(person)
            )
    }

    @PostMapping("/assign")
    fun assignPlaceToUser(
        @Valid @RequestBody assignRequest: AssignPlaceToPersonRequest
    ): ResponseEntity<PersonWithPlacesResponse> {
        val updatedPerson = userService.assignPlaceToUser(assignRequest.personId, assignRequest.placeId)
        return ResponseEntity.ok(updatedPerson)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Unit> {
        userService.deletePersonById(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/user"
    }
}