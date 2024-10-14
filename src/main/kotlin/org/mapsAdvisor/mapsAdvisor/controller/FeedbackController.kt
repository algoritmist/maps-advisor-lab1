package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.PositiveOrZero
import org.mapsAdvisor.mapsAdvisor.controller.FeedbackController.Companion.ROOT_URI
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.response.PlaceFeedbackResponse
import org.mapsAdvisor.mapsAdvisor.response.RouteFeedbackResponse
import org.mapsAdvisor.mapsAdvisor.service.FeedbackService
import org.mapsAdvisor.mapsAdvisor.service.MAX_PAGE_SIZE
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(ROOT_URI)
class FeedbackController(
    private val feedbackService: FeedbackService
) {
    @PostMapping("/route")
    fun createRouteFeedback(@Valid @RequestBody feedback: CreateRouteFeedbackRequest): ResponseEntity<RouteFeedbackResponse> {
        val createdFeedback = feedbackService.createRouteFeedback(feedback)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                RouteFeedbackResponse.fromEntity(createdFeedback)
            )
    }

    @GetMapping("/route/{id}")
    fun getRouteFeedbacks(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<RouteFeedbackResponse>> {
        val feedbacks = feedbackService.getRouteFeedbacks(id, page, size)
        return ResponseEntity.ok(
            feedbacks.map { RouteFeedbackResponse.fromEntity(it) }
        )
    }

    @DeleteMapping("/route/{feedbackId}")
    fun deleteRouteFeedback(@PathVariable feedbackId: String): ResponseEntity<Void> {
        feedbackService.deleteRouteFeedback(feedbackId)
        return ResponseEntity.noContent().build()

    }

    @PostMapping("/place")
    fun createPlaceFeedback(@Valid @RequestBody feedback: CreatePlaceFeedbackRequest): ResponseEntity<PlaceFeedbackResponse> {
        val createdFeedback = feedbackService.createPlaceFeedback(feedback)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PlaceFeedbackResponse.fromEntity(createdFeedback)
            )
    }

    @GetMapping("/place/{id}")
    fun getPlaceFeedbacks(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "0") @PositiveOrZero page: Int,
        @RequestParam(required = false, defaultValue = "50") @PositiveOrZero @Max(MAX_PAGE_SIZE) size: Int
    ): ResponseEntity<List<PlaceFeedbackResponse>> {
        val feedbacks = feedbackService.getPlaceFeedbacks(id, page, size)
        return ResponseEntity.ok(
            feedbacks.map { PlaceFeedbackResponse.fromEntity(it) }
        )
    }

    @DeleteMapping("/place/{id}")
    fun deletePlaceFeedback(@PathVariable id: String): ResponseEntity<Unit> {
        feedbackService.deletePlaceFeedback(id)
        return ResponseEntity.noContent().build()
    }

    companion object {
        const val ROOT_URI = "\${api.prefix}/feedback"
    }
}