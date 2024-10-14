import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import kotlin.test.assertContains

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.entity.Role
import org.mapsAdvisor.mapsAdvisor.entity.Route
import org.mapsAdvisor.mapsAdvisor.request.*
import org.mapsAdvisor.mapsAdvisor.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class FeedbackServiceTest {

    companion object {
        @Autowired
        private lateinit var feedbackService: FeedbackService

        @Autowired
        private lateinit var personService: PersonService

        @Autowired
        private lateinit var placeService: PlaceService

        @Autowired
        private lateinit var routeService: RouteService

        private lateinit var baeldung: Person
        private lateinit var tsopa: Person

        private lateinit var itmo: Place
        private lateinit var hermitage: Place
        private lateinit var isakievsky: Place

        private lateinit var route : Route

        private val baeldungRequest = PersonRequest(
            name = "eugene",
            username = "baeldung",
            password = "baeldung",
            role = Role.USER.name,
            placesOwned = listOf()
        )

        private val tsopaRequest = PersonRequest(
            name = "tsopa",
            username = "tsopa",
            password = "tsopa",
            role = Role.USER.name,
            placesOwned = listOf()
        )

        private val itmoRequest = PlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9563, 30.31),
            tags = listOf("university"),
            owners = listOf(),
            info = "The best university in the world:)"
        )

        private val hermitageRequest = PlaceRequest(
            name = "Hermitage museum",
            coordinates = Coordinates(59.939864, 59.939864),
            tags = listOf("museum"),
            owners = listOf(),
            info = "The famous museum of St. Petersburg"
        )

        private val isakievskyRequest = PlaceRequest(
            name = "Isakievsky Sobor",
            coordinates = Coordinates(59.93391, 30.3064),
        )

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            baeldung = personService.createPerson(baeldungRequest)
            tsopa = personService.createPerson(tsopaRequest)
            itmo = placeService.createPlace(itmoRequest)
            hermitage = placeService.createPlace(hermitageRequest)
            isakievsky = placeService.createPlace(isakievskyRequest)
            route = routeService.createRoute(
                RouteRequest(
                name = "ITMO - Hermitage - Isakievsky",
                description = "Cultural tour from ITMO",
                places = mutableListOf(itmo.id!!, hermitage.id!!, isakievsky.id!!)
            )
            )
        }
    }

    @Test
    fun createRouteFeedback(){
        val request = RouteFeedbackRequest(
            routeId = route.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val routeFeedback = feedbackService.createRouteFeedback(request)
        assertEquals(request.routeId, routeFeedback.routeId)
        assertEquals(request.personId, routeFeedback.grade.personId)
        assertEquals(request.grade, routeFeedback.grade.grade)
    }

    @Test
    fun getRouteFeedbacks(){
        val request1 = RouteFeedbackRequest(
            routeId = route.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val request2 = RouteFeedbackRequest(
            routeId = route.id!!,
            personId = tsopa.id!!,
            grade = 4
        )
        val routeFeedback1 = feedbackService.createRouteFeedback(request1)
        val routeFeedback2 = feedbackService.createRouteFeedback(request2)

        val feedbacks = feedbackService.getRouteFeedbacks(route.id!!, 50, 40)
        assertEquals(2, feedbacks.size)
        assertContains(feedbacks, routeFeedback1)
        assertContains(feedbacks, routeFeedback2)
    }

    @Test
    fun getWrongRouteFeedbacks(){
        assertThrows<NotFoundException> { feedbackService.getRouteFeedbacks("idRoute404", 50, 40) }
    }

    @Test
    fun deleteRouteFeedback(){
        val request = RouteFeedbackRequest(
            routeId = route.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val routeFeedback = feedbackService.createRouteFeedback(request)
        assertDoesNotThrow { feedbackService.deleteRouteFeedback(routeFeedback.id!!) }
    }

    @Test
    fun deleteWrongRouteFeedback(){
        assertThrows<NotFoundException> { feedbackService.deleteRouteFeedback("idRoute404") }
    }

    @Test
    fun createPlaceFeedback(){
        val request = PlaceFeedbackRequest(
            placeId = hermitage.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val routeFeedback = feedbackService.createPlaceFeedback(request)
        assertEquals(request.placeId, routeFeedback.placeId)
        assertEquals(request.personId, routeFeedback.grade.personId)
        assertEquals(request.grade, routeFeedback.grade.grade)
    }

    @Test
    fun getPlaceFeedbacks(){
        val request1 = PlaceFeedbackRequest(
            placeId = hermitage.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val request2 = PlaceFeedbackRequest(
            placeId = hermitage.id!!,
            personId = tsopa.id!!,
            grade = 4
        )
        val placeFeedback1 = feedbackService.createPlaceFeedback(request1)
        val placeFeedback2 = feedbackService.createPlaceFeedback(request2)

        val feedbacks = feedbackService.getPlaceFeedbacks(route.id!!, 50, 40)
        assertEquals(2, feedbacks.size)
        assertContains(feedbacks, placeFeedback1)
        assertContains(feedbacks, placeFeedback2)
    }

    @Test
    fun getWrongPlaceFeedbacks(){
        assertThrows<NotFoundException> { feedbackService.getPlaceFeedbacks("idPlace404", 50, 40) }
    }

    @Test
    fun deletePlaceFeedback(){
        val request = PlaceFeedbackRequest(
            placeId = hermitage.id!!,
            personId = baeldung.id!!,
            grade = 5
        )
        val placeFeedback = feedbackService.createPlaceFeedback(request)
        assertDoesNotThrow { feedbackService.deletePlaceFeedback(placeFeedback.id!!) }
    }

    @Test
    fun deleteWrongPlaceFeedback(){
        assertThrows<NotFoundException> { feedbackService.deletePlaceFeedback("idPlace404") }
    }
}