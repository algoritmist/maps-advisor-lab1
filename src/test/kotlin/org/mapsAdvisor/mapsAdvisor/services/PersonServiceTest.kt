package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.entity.Role
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.CreatePersonRequest
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.abs
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersonServiceTest {
    private val personRepository: PersonRepository = mock<PersonRepository>()
    private val placeRepository: PlaceRepository = mock<PlaceRepository>()
    private val personService = PersonService(personRepository, placeRepository)


    private val baeldungRequest = CreatePersonRequest(
        name ="eugene",
        username = "baeldung",
        password = "baeldung",
        role = Role.USER.name,
        placesOwned = listOf()
    )

    @Test
    fun `create person should create person`(){
        /*val person = personService.createPerson(baeldungRequest)
        assertEquals(baeldungRequest.name, person.name)
        assertEquals(baeldungRequest.username, person.username)
        assertEquals(baeldungRequest.password, person.password)
        assertEquals(Role.USER, person.role)
        assertTrue { personRepository.existsById(person.id!!) }*/
    }

    @Test
    fun `create person should throw IllegalArgumentException`(){
        whenever(personRepository.existsByUsername("baeldung")).thenReturn(true)
        assertThrows<IllegalArgumentException> { personService.createPerson(baeldungRequest) }
    }

    @Test
    fun `assign place to user should assign place to user`(){
        /*val person = mock<Person>()
        person.placesOwned = listOf()
        whenever(person.id).thenReturn("__id1")
        whenever(person.username).thenReturn("baeldung")
        whenever(personRepository.findById("__id1")).thenReturn(Optional.of(person))

        val place = mock<Place>()
        place.owners = listOf()
        whenever(place.id).thenReturn("__id1")
        whenever(placeRepository.findById("__id1")).thenReturn(Optional.of(place))

        val personWithPlaces = personService.assignPlaceToUser(person.id!!, place.id!!)
        assertEquals(person.username, personWithPlaces.username)
        assertContains(personWithPlaces.places.stream().map { p -> p.id }.toList(), place.id!!)
        assertContains(place.owners, person.id!!)
        assertContains(person.placesOwned, place.id!!)*/
    }

    @Test
    fun `assign place to unexisting user should throw NotFoundException`(){
        val personId = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        whenever(personRepository.findById(personId)).thenReturn(Optional.empty())
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.of(mock<Place>()))
        assertThrows<NotFoundException> {personService.assignPlaceToUser(personId, placeId)}
    }

    @Test
    fun `assign unexisting place to user should throw NotFoundException`(){
        val personId = UUID.randomUUID().toString()
        val placeId = UUID.randomUUID().toString()
        whenever(personRepository.findById(personId)).thenReturn(Optional.of(mock<Person>()))
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> {personService.assignPlaceToUser(personId, placeId)}
    }

    @Test
    fun `delete person by id should delete person`(){
        val personId = UUID.randomUUID().toString()
        whenever(personRepository.findById(personId)).thenReturn(Optional.of(mock<Person>()))
        assertDoesNotThrow { personService.deletePersonById(personId) }
    }

    @Test
    fun `delete person by id should throw NotFoundException`(){
        val personId = UUID.randomUUID().toString()
        whenever(personRepository.findById(personId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { personService.deletePersonById(personId) }
    }
}