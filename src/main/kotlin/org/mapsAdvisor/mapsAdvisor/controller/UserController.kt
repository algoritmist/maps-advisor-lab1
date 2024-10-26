package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.controller.UserController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.request.AssignPlaceToPersonRequest
import org.mapsAdvisor.mapsAdvisor.model.request.CreateUserRequest
import org.mapsAdvisor.mapsAdvisor.model.response.PersonResponse
import org.mapsAdvisor.mapsAdvisor.model.response.PersonWithPlacesResponse
import org.mapsAdvisor.mapsAdvisor.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class UserController(
    private val userService: UserService
) {
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<PersonResponse> {
        val person = userService.getUserById(id) ?: throw NotFoundException("User with id $id not found")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                PersonResponse.fromEntity(person)
            )
    }

    @PostMapping("/signup")
    fun createUser(@Valid @RequestBody newUser: CreateUserRequest): ResponseEntity<PersonResponse> {
        val person = userService.createUser(newUser)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PersonResponse.fromEntity(person)
            )
    }

    @PatchMapping("/assign")
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