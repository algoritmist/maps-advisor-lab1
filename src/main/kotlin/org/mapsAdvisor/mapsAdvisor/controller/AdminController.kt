package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.controller.AdminController.Companion.ROOT_URI

import org.mapsAdvisor.mapsAdvisor.model.request.AssignPlaceToOwnerRequest
import org.mapsAdvisor.mapsAdvisor.model.response.UserResponse
import org.mapsAdvisor.mapsAdvisor.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping(ROOT_URI)
class AdminController(
    private val userService: UserService
) {
    @PatchMapping("/assign")
    fun assignPlaceToOwner(
        @Valid @RequestBody assignRequest: AssignPlaceToOwnerRequest
    ): ResponseEntity<UserResponse> {
        val updatedPerson = userService.assignPlaceToOwner(assignRequest.personId, assignRequest.placeId)
        return ResponseEntity.ok(updatedPerson)
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/admin"
    }
}