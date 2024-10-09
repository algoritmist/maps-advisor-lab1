import junit.framework.TestCase.assertEquals
import kotlin.test.assertEquals

.package org.mapsAdvisor.mapsAdvisor

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
class FeedbaclkServiceTest {
    @Autowired
    private lateinit var feedbackService: FeedbackService

    companion object {
        @Autowired
        private lateinit var favoritesService: FavoritesService

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
        val route = feedbackService.createRouteFeedback(request)
        assertEquals(request.routeId, route.routeId)
        assertEquals(request.personId, route.grade.personId)
        assertEquals(request.grade, route.grade.grade)
    }

    @Test
    fun
}