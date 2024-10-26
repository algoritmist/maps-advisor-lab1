package org.mapsAdvisor.mapsAdvisor.integration

import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.model.entity.Grade
import org.mapsAdvisor.mapsAdvisor.model.entity.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.assertj.core.api.Assertions.assertThat
import org.mapsAdvisor.mapsAdvisor.model.entity.RouteFeedback

class FeedbackRepositoryIntegrationTest: IntegrationEnvironment() {
    @Autowired
    private lateinit var routeFeedbackRepository: RouteFeedbackRepository
    @Autowired
    private lateinit var placeFeedbackRepository: PlaceFeedbackRepository

    @Test
    fun givenPlaceFeedbackRepository_whenFindByPlaceIdWithPagination_thenOK() {
        val feedback1 = PlaceFeedback(id = "401", placeId = "place1", grade = Grade(personId = "123", grade = 5))
        val feedback2 = PlaceFeedback(id = "402", placeId = "place1", grade = Grade(personId = "134", grade = 4))
        placeFeedbackRepository.saveAll(listOf(feedback1, feedback2))

        val pageable: Pageable = PageRequest.of(0, 10)
        val feedbackPage = placeFeedbackRepository.findByPlaceId("place1", pageable)

        assertThat(feedbackPage.content.size).isGreaterThanOrEqualTo(2)
        assertThat(feedbackPage.content).contains(feedback1, feedback2)
    }

    @Test
    fun givenPlaceFeedbackRepository_whenDeleteAllByPlaceId_thenNotFound() {
        val feedback1 = PlaceFeedback(id = "403", placeId = "place2", grade = Grade(personId = "123", grade = 5))
        val feedback2 = PlaceFeedback(id = "404", placeId = "place2", grade = Grade(personId = "124", grade = 4))
        placeFeedbackRepository.saveAll(listOf(feedback1, feedback2))

        placeFeedbackRepository.deleteAllByPlaceId("place2")

        val feedbackPageAfterDeletion = placeFeedbackRepository.findByPlaceId("place2", PageRequest.of(0, 50))
        assertThat(feedbackPageAfterDeletion.content).isEmpty()
    }

    @Test
    fun givenRouteFeedbackRepository_whenFindByRouteIdWithPagination_thenOK() {
        val feedback1 = RouteFeedback(id = "401", routeId = "place1", grade = Grade(personId = "123", grade = 5))
        val feedback2 = RouteFeedback(id = "402", routeId = "place1", grade = Grade(personId = "134", grade = 4))
        routeFeedbackRepository.saveAll(listOf(feedback1, feedback2))

        val pageable: Pageable = PageRequest.of(0, 10)
        val feedbackPage = routeFeedbackRepository.findByRouteId("place1", pageable)

        assertThat(feedbackPage.content.size).isGreaterThanOrEqualTo(2)
        assertThat(feedbackPage.content).contains(feedback1, feedback2)
    }

    @Test
    fun givenRouteFeedbackRepository_whenDeleteAllByRouteId_thenNotFound() {
        val feedback1 = RouteFeedback(id = "403", routeId = "place2", grade = Grade(personId = "123", grade = 5))
        val feedback2 = RouteFeedback(id = "404", routeId = "place2", grade = Grade(personId = "124", grade = 4))
        routeFeedbackRepository.saveAll(listOf(feedback1, feedback2))

        routeFeedbackRepository.deleteAllByRouteId("123")

        val feedbackPageAfterDeletion = routeFeedbackRepository.findByRouteId("123", PageRequest.of(0, 50))
        assertThat(feedbackPageAfterDeletion.content).isEmpty()
    }
}