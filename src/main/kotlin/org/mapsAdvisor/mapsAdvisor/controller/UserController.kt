package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.controller.UserController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.request.CreateUserRequest
import org.mapsAdvisor.mapsAdvisor.model.response.UserResponse
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
    @PostMapping
    fun createUser(@Valid @RequestBody newUser: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(newUser)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                UserResponse.fromEntity(user)
            )
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserResponse> {
        val user = userService.getUser(id) ?: throw NotFoundException("User with id $id not found")
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                UserResponse.fromEntity(user)
            )
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/user"
    }
}