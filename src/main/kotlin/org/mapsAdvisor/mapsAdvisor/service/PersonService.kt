package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.mapsAdvisor.mapsAdvisor.entity.Role
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.CreatePersonRequest
import org.mapsAdvisor.mapsAdvisor.response.PersonWithPlacesResponse
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val placeRepository: PlaceRepository
) {
    fun getPerson(id: String): Person {
        return personRepository.findById(id)
            .orElseThrow { NotFoundException("Person with id $id not found") }
    }

    fun createPerson(request: CreatePersonRequest): Person {
        if (personRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("User with username ${request.username} already exists")
        }

        return try {
            personRepository.save(
                Person(
                    name = request.name,
                    username = request.username,
                    password = request.password,
                    role = Role.valueOf(request.role.uppercase()),
                    placesOwned = request.placesOwned,
                    registrationDate = Instant.now(),
                )
            )
        } catch (ex: DataAccessException) {
            throw IllegalStateException("Failed to create user due to a database error", ex)
        }
    }

    @Transactional
    fun assignPlaceToUser(personId: String, placeId: String): PersonWithPlacesResponse {
        val person = personRepository.findById(personId)
            .orElseThrow { NotFoundException("Person with id $personId not found") }

        val place = placeRepository.findById(placeId)
            .orElseThrow { NotFoundException("Place with id $placeId not found") }

        if (place.owners.contains(personId)) {
            throw IllegalStateException("Person already owns this place")
        }

        if (person.role == Role.USER) {
            person.role = Role.OWNER
        }

        person.placesOwned += placeId
        place.owners = listOf(personId)

        personRepository.save(person)
        placeRepository.save(place)

        val places = placeRepository.findAllById(person.placesOwned)

        return PersonWithPlacesResponse.fromEntities(person, places)
    }

    @Transactional
    fun deletePersonById(personId: String) {
        val personToDelete = personRepository.findById(personId)
            .orElseThrow { NotFoundException("Person with id $personId not found") }

        try {
            personRepository.delete(personToDelete)
        } catch (ex: DataAccessException) {
            throw IllegalStateException("Failed to delete user due to a database error", ex)
        }

        placeRepository.deleteAllByOwnersContains(personId)
    }
}