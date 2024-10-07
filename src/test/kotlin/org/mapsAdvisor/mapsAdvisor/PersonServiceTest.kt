package org.mapsAdvisor.mapsAdvisor

import com.mongodb.assertions.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.runner.RunWith
import org.mapsAdvisor.mapsAdvisor.entity.Role
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.mapsAdvisor.mapsAdvisor.request.PlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(SpringRunner::class)
@SpringBootTest
class PersonServiceTest {
    @Autowired
	private lateinit var personService: PersonService
    @Autowired
    private lateinit var placeService: PlaceService

    private val baeldung = PersonRequest(
        name ="eugene",
        username = "baeldung",
        password = "baeldung",
        role = Role.USER.name,
        placesOwned = listOf()
    )

    /// TODO: remove all entities from service

	@ParameterizedTest
    @EnumSource(Role::class)
	fun createPerson(){
		val user = personService.createPerson(baeldung)
        assertNotNull(user.id)
        assertEquals(user.name, "eugene")
        assertEquals(user.username, "baeldung")
        assertEquals(user.password, "baeldung")
        assertEquals(user.role, Role.USER)
        assertTrue(user.placesOwned.isEmpty())
        assertNotNull(user.registrationDate)
	}

    @Test
    fun createPersonWrongRole(){
        val person = PersonRequest(
            name ="eugene",
            username = "baeldung",
            password = "baeldung",
            role = "Watcher",
            placesOwned = listOf()
        )
        assertThrows<IllegalArgumentException> { personService.createPerson(person) }
    }

    @Test
    fun createExistingUser(){
        personService.createPerson(baeldung)
        assertThrows<IllegalArgumentException> { personService.createPerson(baeldung) }
    }

    /// TODO: should this be integration test?
    @Test
    fun assignPlaceToUser(){
        val person = personService.createPerson(baeldung)
        val placeRequest = PlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9572, 30.3083),
            tags = listOf(),
            owners = listOf(person.id!!),
            info = "The best university in the world:)"
        )
        val place = placeService.createPlace(placeRequest)

        val personWithPlaces = personService.assignPlaceToUser(person.id!!, place.id!!)

        assertNotNull(personWithPlaces)
        assertEquals(personWithPlaces.username, person.id)
        assertEquals(1, personWithPlaces.places.size)
        val personPlace = personWithPlaces.places.first()
        assertEquals(place.name, personPlace.name)
    }

    @Test
    fun assignPlaceToUserTwice(){
        assignPlaceToUser();
        assertThrows<IllegalArgumentException> { assignPlaceToUser() }
    }

    @Test
    fun assignPlaceToWrongUser(){
        val dummyPersonId = "idPerson404"
        val placeRequest = PlaceRequest(
            name = "ITMO University",
            coordinates = Coordinates(59.9572, 30.3083),
            tags = listOf(),
            owners = listOf(dummyPersonId),
            info = "The best university in the world:)"
        )
        val place = placeService.createPlace(placeRequest)
        assertThrows<NotFoundException> { personService.assignPlaceToUser(dummyPersonId, place.id!!) }
    }

    @Test
    fun assignWrongPlaceToUser(){
        val person = personService.createPerson(baeldung)
        val dummyPlaceId = "idPlace404"
        assertThrows<NotFoundException> { personService.assignPlaceToUser(dummyPlaceId, dummyPlaceId) }
    }

    @Test
    fun deleteUserById(){
        val person = personService.createPerson(baeldung)
        assertDoesNotThrow { personService.deletePersonById(person.id!!) }
    }

    @Test
    fun deleteWrongUserById(){
        assertThrows<NotFoundException> { personService.deletePersonById("idPerson404") }
    }

}
