package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.PlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.RouteFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.response.PlaceFeedbackResponse
import org.mapsAdvisor.mapsAdvisor.response.RouteFeedbackResponse
import org.mapsAdvisor.mapsAdvisor.service.FeedbackService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api/feedback")
class FeedbackController(
    private val feedbackService: FeedbackService
) {
    @PostMapping("/route")
    fun createRouteFeedback(@Valid @RequestBody feedback: RouteFeedbackRequest): ResponseEntity<RouteFeedbackResponse> {
        val createdFeedback = feedbackService.createRouteFeedback(feedback)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                RouteFeedbackResponse.fromEntity(createdFeedback)
            )
    }

    @GetMapping("/route")
    fun getRouteFeedbacks(
        @RequestParam routeId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<Page<RouteFeedbackResponse>> {
        val feedbacks = feedbackService.getRouteFeedbacks(routeId, page, size)
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
    fun createPlaceFeedback(@Valid @RequestBody feedback: PlaceFeedbackRequest): ResponseEntity<PlaceFeedbackResponse> {
        val createdFeedback = feedbackService.createPlaceFeedback(feedback)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                PlaceFeedbackResponse.fromEntity(createdFeedback)
            )
    }

    @GetMapping("/place")
    fun getPlaceFeedbacks(
        @RequestParam routeId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "50") size: Int
    ): ResponseEntity<Page<PlaceFeedbackResponse>> {
        val feedbacks = feedbackService.getPlaceFeedbacks(routeId, page, size)
        return ResponseEntity.ok(
            feedbacks.map { PlaceFeedbackResponse.fromEntity(it) }
        )
    }

    @DeleteMapping("/place/{feedbackId}")
    fun deletePlaceFeedback(@PathVariable feedbackId: String): ResponseEntity<Void> {
        feedbackService.deletePlaceFeedback(feedbackId)
        return ResponseEntity.noContent().build()
    }
}