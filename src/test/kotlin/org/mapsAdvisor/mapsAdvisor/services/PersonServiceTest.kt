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
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
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

    @Test
    fun `create person should create person`(){
        val person = Person(
            id = UUID.randomUUID().toString(),
            name = "baeldung",
            username = "baeldung",
            password = "baeldung",
            role = Role.USER,
            placesOwned = listOf(),
            registrationDate = Instant.now()
        )

        whenever(personRepository.save(any<Person>())).thenReturn(person)

        val result = personService.createPerson(
            CreatePersonRequest(
                name = person.name,
                username = person.username,
                password = person.password,
                role = "USER",
                placesOwned = listOf()
            )
        )
        assertEquals(person, result)
    }

    @Test
    fun `create person should throw IllegalArgumentException`(){
        whenever(personRepository.existsByUsername("baeldung")).thenReturn(true)
        val person = mock<CreatePersonRequest>()
        person.name = "baeldung"
        assertThrows<IllegalArgumentException> { personService.createPerson(person) }
    }

    @Test
    fun `assign place to user should assign place to user`(){
        val person = Person(
            id = UUID.randomUUID().toString(),
            name = "baeldung",
            username = "baeldung",
            password = "baeldung",
            role = Role.USER,
            placesOwned = listOf(),
            registrationDate = Instant.now()
        )
        whenever(personRepository.findById(person.id!!)).thenReturn(Optional.of(person))

        val place = Place(
            id = UUID.randomUUID().toString(),
            name = UUID.randomUUID().toString(),
            coordinates = GeoJsonPoint(50.0, 40.0),
            tags = listOf(),
            owners = listOf(),
            info = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
        )
        whenever(placeRepository.findById(place.id!!)).thenReturn(Optional.of(place))
        whenever(placeRepository.findAllById(listOf(place.id!!))).thenReturn(listOf(place))

    val response = personService.assignPlaceToUser(person.id!!, place.id!!)
        assertContains(person.placesOwned, place.id!!)
        assertEquals(person.role, Role.OWNER)
        assertContains(place.owners, person.id!!)
        assertEquals(response.username, person.username)
        assertContains(response.places.stream().map { r -> r.id }.toList(), place.id!!)
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