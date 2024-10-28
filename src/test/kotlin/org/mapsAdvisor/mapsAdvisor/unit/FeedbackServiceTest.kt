package org.mapsAdvisor.mapsAdvisor.unit

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.model.entity.*
import org.mapsAdvisor.mapsAdvisor.model.request.CreatePlaceFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.model.request.CreateRouteFeedbackRequest
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.RouteFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.service.FeedbackService
import org.mapsAdvisor.mapsAdvisor.service.UserService
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.mapsAdvisor.mapsAdvisor.service.RouteService
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals

class FeedbackServiceTest {
    private val routeFeedbackRepository = mock<RouteFeedbackRepository>()
    private val placeFeedbackRepository = mock<PlaceFeedbackRepository>()
    private val routeService = mock<RouteService>()
    private val placeService = mock<PlaceService>()
    private val personService = mock<UserService>()

    private val feedbackService =
        FeedbackService(routeFeedbackRepository, placeFeedbackRepository, routeService, placeService, personService)

    @Test
    fun `test createRouteFeedback successfully creates route feedback`() {
        val id = UUID.randomUUID().toString()
        val routeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(routeService.getRoute(routeId)).thenReturn(Route(id, "aboba", "aboba"))
        whenever(personService.getUser(personId)).thenReturn(
            Person(
                id,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        val routeFeedback = RouteFeedback(
            id = id,
            routeId = routeId,
            grade = Grade(personId = personId, grade = 5)
        )
        whenever(routeFeedbackRepository.save(any<RouteFeedback>())).thenReturn(routeFeedback)
        val request = CreateRouteFeedbackRequest(
            routeId = routeId,
            personId = personId,
            grade = 5
        )
        assertEquals(routeFeedback, feedbackService.createRouteFeedback(request))
    }

    @Test
    fun `test createRouteFeedback throws NotFoundException when route not found`() {
        val routeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(routeService.getRoute(routeId)).thenReturn(null)
        whenever(personService.getUser(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )

        assertThrows<NotFoundException> {
            feedbackService.createRouteFeedback(
                CreateRouteFeedbackRequest(
                    routeId = routeId,
                    personId = personId,
                    grade = 5
                )
            )
        }
    }

    @Test
    fun `test createRouteFeedback throws NotFoundException when person not found`() {
        val personId = UUID.randomUUID().toString()
        val routeId = UUID.randomUUID().toString()
        whenever(routeService.getRoute(routeId)).thenReturn(Route("123", "aboba", "aboba"))
        whenever(personService.getUser(personId)).thenReturn(null)

        assertThrows<NotFoundException> {
            feedbackService.createRouteFeedback(
                CreateRouteFeedbackRequest(
                    routeId = routeId,
                    personId = personId,
                    grade = 5
                )
            )
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getRouteFeedbacks returns paginated route feedbacks`(pageSize: Int) {

        val routeId = UUID.randomUUID().toString()
        val page = PageRequest.of(0, pageSize)
        val lst = mock<List<RouteFeedback>>()
        whenever(routeService.getRoute(routeId)).thenReturn(Route("123", "aboba", "aboba"))
        whenever(lst.size).thenReturn(pageSize)
        whenever(routeFeedbackRepository.findByRouteId(routeId, page)).thenReturn(mock<Page<RouteFeedback>>())
        whenever(routeFeedbackRepository.findByRouteId(routeId, page).content).thenReturn(lst)
        val lstGot = feedbackService.getRouteFeedbacks(routeId, 0, pageSize)
        assertEquals(lst.size, lstGot.size)
    }

    @Test
    fun `test getRouteFeedbacks throws NotFoundException when route not found`() {
        val routeId = UUID.randomUUID().toString()
        whenever(routeService.getRoute(routeId)).thenReturn(null)
        assertThrows<NotFoundException> { feedbackService.getRouteFeedbacks(routeId, 50, 40) }
    }

    @Test
    fun `test deleteRouteFeedback successfully deletes feedback`() {
        val routeId = UUID.randomUUID().toString()
        whenever(routeFeedbackRepository.existsById(routeId)).thenReturn(true)
        assertDoesNotThrow { feedbackService.deleteRouteFeedback(routeId) }
    }

    @Test
    fun `test deleteRouteFeedback throws NotFoundException when feedback does not exist`() {
        val feedbackId = UUID.randomUUID().toString()
        whenever(routeFeedbackRepository.existsById(feedbackId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.deleteRouteFeedback(feedbackId) }
    }

    @Test
    fun `test createPlaceFeedback successfully creates place feedback`() {
        val id = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeService.getPlace(placeId)).thenReturn(Place(id, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(personService.getUser(personId)).thenReturn(
            Person(
                id,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        val placeFeedback = PlaceFeedback(
            id = id,
            placeId = placeId,
            grade = Grade(personId = personId, grade = 5)
        )
        whenever(placeFeedbackRepository.save(any<PlaceFeedback>())).thenReturn(placeFeedback)
        val request = CreatePlaceFeedbackRequest(
            placeId = placeId,
            personId = personId,
            grade = 5
        )
        assertEquals(placeFeedback, feedbackService.createPlaceFeedback(request))
    }

    @Test
    fun `test createPlaceFeedback throws NotFoundException when place not found`() {
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeService.getPlace(placeId)).thenReturn(null)
        whenever(personService.getUser(personId)).thenReturn(
            Person(
                personId,
                "a",
                "a",
                "a",
                Role.USER,
                listOf(),
                Instant.now()
            )
        )
        assertThrows<NotFoundException> {
            feedbackService.createPlaceFeedback(
                CreatePlaceFeedbackRequest(
                    placeId = placeId,
                    personId = personId,
                    grade = 5
                )
            )
        }
    }

    @Test
    fun `test createPlaceFeedback throws NotFoundException when person not found`() {
        val placeId = UUID.randomUUID().toString()
        val personId = UUID.randomUUID().toString()
        whenever(placeService.getPlace(placeId)).thenReturn(Place(placeId, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(personService.getUser(personId)).thenReturn(null)
        assertThrows<NotFoundException> {
            feedbackService.createPlaceFeedback(
                CreatePlaceFeedbackRequest(
                    placeId = placeId,
                    personId = personId,
                    grade = 5
                )
            )
        }
    }


    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `test getPlaceFeedbacks returns paginated place feedbacks`(pageSize: Int) {
        val placeId = UUID.randomUUID().toString()
        val pageRequest = PageRequest.of(0, pageSize)
        val lst = mock<List<PlaceFeedback>>()

        whenever(placeService.getPlace(placeId)).thenReturn(Place(placeId, "aboba", GeoJsonPoint(2.0, 4.0)))
        whenever(lst.size).thenReturn(pageSize)
        whenever(placeFeedbackRepository.findByPlaceId(placeId, pageRequest)).thenReturn(mock<Page<PlaceFeedback>>())
        whenever(placeFeedbackRepository.findByPlaceId(placeId, pageRequest).content).thenReturn(lst)
        val lstGot = feedbackService.getPlaceFeedbacks(placeId, 0, pageSize)

        assertEquals(lst.size, lstGot.size)
    }

    @Test
    fun `test getPlaceFeedbacks throws NotFoundException when place not found`() {
        val placeId = UUID.randomUUID().toString()
        whenever(placeService.getPlace(placeId)).thenReturn(null)
        assertThrows<NotFoundException> { feedbackService.getPlaceFeedbacks(placeId, 50, 40) }
    }

    @Test
    fun `test deletePlaceFeedback successfully deletes feedback`() {
        whenever(placeFeedbackRepository.existsById(anyString())).thenReturn(true)
        assertDoesNotThrow { feedbackService.deletePlaceFeedback(UUID.randomUUID().toString()) }
    }

    @Test
    fun `test deletePlaceFeedback throws NotFoundException when feedback does not exist`() {
        val feedbackId = UUID.randomUUID().toString()
        whenever(placeFeedbackRepository.existsById(feedbackId)).thenReturn(false)
        assertThrows<NotFoundException> { feedbackService.deletePlaceFeedback(feedbackId) }
    }
}