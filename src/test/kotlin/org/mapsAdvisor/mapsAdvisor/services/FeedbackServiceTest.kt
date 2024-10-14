package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.mapsAdvisor.mapsAdvisor.entity.RouteFeedback
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.*
import org.mapsAdvisor.mapsAdvisor.request.PlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.request.RouteFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.service.FeedbackService
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.*

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
            RouteFeedbackRequest(
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
            RouteFeedbackRequest(
                routeId = routeId,
                personId = personId,
                grade = 5
            )
        )  }
    }

    @Test
    fun `test getRouteFeedbacks returns paginated route feedbacks`(){}

    @Test
    fun `test getRouteFeedbacks throws NotFoundException when route not found`(){
        val routeId = UUID.randomUUID().toString()
        whenever(routeRepository.existsById(routeId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.getRouteFeedbacks(routeId, 50, 40) }
    }

    @Test
    fun `test deleteRouteFeedback successfully deletes feedback`(){}

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
        assertThrows<NotFoundException> { feedbackService.createPlaceFeedback(PlaceFeedbackRequest(
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
        assertThrows<NotFoundException> { feedbackService.createPlaceFeedback(PlaceFeedbackRequest(
            placeId = placeId,
            personId = personId,
            grade = 5
        ))}
    }

    @Test
    fun `test getPlaceFeedbacks returns paginated place feedbacks`(){}

    @Test
    fun `test getPlaceFeedbacks throws NotFoundException when place not found`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.getPlaceFeedbacks(placeId, 50, 40) }
    }

    @Test
    fun `test deletePlaceFeedback successfully deletes feedback`(){}

    @Test
    fun `test deletePlaceFeedback throws NotFoundException when feedback does not exist`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.existsById(placeId)).thenReturn(false)
        assertThrows<NotFoundException> {feedbackService.deletePlaceFeedback(placeId)  }
    }
}