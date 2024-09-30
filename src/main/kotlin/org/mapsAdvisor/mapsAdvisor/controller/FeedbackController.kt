package org.mapsAdvisor.mapsAdvisor.controller

import jakarta.validation.Valid
import org.mapsAdvisor.mapsAdvisor.model.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.model.RouteFeedback
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
    fun createRouteFeedback(@Valid @RequestBody feedback: RouteFeedback): ResponseEntity<RouteFeedback> {
        val createdFeedback = feedbackService.createRouteFeedback(feedback)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback)
    }

    @GetMapping("/route")
    fun getRouteFeedbacks(
        @RequestParam routeId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<Page<RouteFeedback>> {
        val feedbacks = feedbackService.getRouteFeedbacks(routeId, page)
        return ResponseEntity.ok(feedbacks)
    }

    @DeleteMapping("/route/{feedbackId}")
    fun deleteRouteFeedback(@PathVariable feedbackId: String): ResponseEntity<Void> {
        feedbackService.deleteRouteFeedback(feedbackId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/place")
    fun createPlaceFeedback(@Valid @RequestBody feedback: PlaceFeedback): ResponseEntity<PlaceFeedback> {
        val createdFeedback = feedbackService.createPlaceFeedback(feedback)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback)
    }

    @GetMapping("/place")
    fun getPlaceFeedbacks(
        @RequestParam routeId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): ResponseEntity<Page<PlaceFeedback>> {
        val feedbacks = feedbackService.getPlaceFeedbacks(routeId, page)
        return ResponseEntity.ok(feedbacks)
    }

    @DeleteMapping("/place/{feedbackId}")
    fun deletePlaceFeedback(@PathVariable feedbackId: String): ResponseEntity<Void> {
        feedbackService.deletePlaceFeedback(feedbackId)
        return ResponseEntity.noContent().build()
    }

}