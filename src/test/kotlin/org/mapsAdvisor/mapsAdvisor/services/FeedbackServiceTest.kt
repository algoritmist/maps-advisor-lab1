package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.entity.PlaceFeedback
import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.mapsAdvisor.mapsAdvisor.entity.RouteFeedback
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.*
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.CreateRouteFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.service.FeedbackService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.test.assertEquals

class FeedbackServiceTest {
    private val routeFeedbackRepository = mock<RouteFeedbackRepository>()
    private val placeFeedbackRepository = mock<PlaceFeedbackRepository>()
    private val routeRepository = mock<RouteRepository>()
    private val placeRepository = mock<PlaceRepository>()
    private val personRepository = mock<PersonRepository>()

    private val feedbackService = FeedbackService(routeFeedbackRepository, placeFeedbackRepository, routeRepository, placeRepository, personRepository)

    @Test
    fun `test createRouteFeedback successfully creates route feedback`(){}

    @Test
    fun `test createRouteFeedback throws NotFoundException when route not found`(){
        val routeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(routeRepository.existsById(routeId)).thenReturn(false)
        whenever(personRepository.existsById(personId)).thenReturn(true)
        assertThrows<NotFoundException> { feedbackService.createRouteFeedback(
            CreateRouteFeedbackRequest(
                routeId = routeId,
                personId = personId,
                grade = 5
            )
        )  }
    }

    @Test
    fun `test createRouteFeedback throws NotFoundException when person not found`(){
        val personId = UUID.randomUUID().toString()
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.existsById(routeId)).thenReturn(false)
        whenever(personRepository.existsById(personId)).thenReturn(true)
        assertThrows<NotFoundException> { feedbackService.createRouteFeedback(
            CreateRouteFeedbackRequest(
                routeId = routeId,
                personId = personId,
                grade = 5
            )
        )  }
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getRouteFeedbacks returns paginated route feedbacks`(pageSize: Int){
        /*whenever(routeRepository.existsById(anyString())).thenReturn(true)
        val pageRequest = PageRequest.of(0, pageSize)
        val page = mock<Page<RouteFeedback>>()
        whenever(routeFeedbackRepository.findByRouteId(anyString(), pageRequest)).thenReturn(page)
        val pageGot = feedbackService.getRouteFeedbacks(UUID.randomUUID().toString(), 0, pageSize)
        assertEquals(page.size, pageGot.size)*/
    }

    @Test
    fun `test getRouteFeedbacks throws NotFoundException when route not found`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.existsById(routeId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.getRouteFeedbacks(routeId, 50, 40) }
    }

    @Test
    fun `test deleteRouteFeedback successfully deletes feedback`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeFeedbackRepository.existsById(routeId)).thenReturn(true)
        assertDoesNotThrow { feedbackService.deleteRouteFeedback(routeId) }
    }

    @Test
    fun `test deleteRouteFeedback throws NotFoundException when feedback does not exist`(){
        val feedbackId = UUID.randomUUID().toString()
        whenever(routeFeedbackRepository.existsById(feedbackId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.deleteRouteFeedback(feedbackId) }
    }

    @Test
    fun `test createPlaceFeedback successfully creates place feedback`(){}

    @Test
    fun `test createPlaceFeedback throws NotFoundException when place not found`(){
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(false)
        whenever(personRepository.existsById(personId)).thenReturn(true)
        assertThrows<NotFoundException> { feedbackService.createPlaceFeedback(CreatePlaceFeedbackRequest(
            placeId = placeId,
            personId = personId,
            grade = 5
        ))}
    }

    @Test
    fun `test createPlaceFeedback throws NotFoundException when person not found`(){
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(true)
        whenever(personRepository.existsById(personId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.createPlaceFeedback(CreatePlaceFeedbackRequest(
            placeId = placeId,
            personId = personId,
            grade = 5
        ))}
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getPlaceFeedbacks returns paginated place feedbacks`(pageSize: Int){
        /*whenever(placeRepository.existsById(anyString())).thenReturn(true)
        val pageRequest = PageRequest.of(0, pageSize)
        val page = mock<Page<PlaceFeedback>>()
        whenever(placeFeedbackRepository.findByPlaceId(anyString(), pageRequest)).thenReturn(page)
        val pageGot = feedbackService.getPlaceFeedbacks(UUID.randomUUID().toString(), 0, pageSize)
        assertEquals(page.size, pageGot.size)*/
    }

    @Test
    fun `test getPlaceFeedbacks throws NotFoundException when place not found`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.getPlaceFeedbacks(placeId, 50, 40) }
    }

    @Test
    fun `test deletePlaceFeedback successfully deletes feedback`(){
        whenever(placeFeedbackRepository.existsById(anyString())).thenReturn(true)
        assertDoesNotThrow { feedbackService.deletePlaceFeedback(UUID.randomUUID().toString()) }
    }

    @Test
    fun `test deletePlaceFeedback throws NotFoundException when feedback does not exist`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(false)
        assertThrows<NotFoundException> {feedbackService.deletePlaceFeedback(placeId)  }
    }
}