package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.exception.DuplicateException
import org.mapsAdvisor.mapsAdvisor.model.entity.Person
import org.mapsAdvisor.mapsAdvisor.model.entity.Role
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.model.request.CreateUserRequest
import org.mapsAdvisor.mapsAdvisor.model.response.PersonWithPlacesResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class UserService(
    private val personRepository: PersonRepository,
    private val placeRepository: PlaceRepository
) {
    fun getUserById(id: String): Person? {
        return personRepository.findByIdOrNull(id)
    }

    fun createUser(request: CreateUserRequest): Person {
        if (personRepository.existsByUsername(request.username)) {
            throw DuplicateException("User with username ${request.username} already exists")
        }

        return personRepository.save(
            Person(
                name = request.name,
                username = request.username,
                password = request.password,
                role = Role.USER,
                registrationDate = Instant.now(),
            )
        )
    }

    @Transactional
    fun assignPlaceToUser(personId: String, placeId: String): PersonWithPlacesResponse {
        val person = personRepository.findById(personId)
            .orElseThrow { NotFoundException("Person with id $personId not found") }

        val place = placeRepository.findById(placeId)
            .orElseThrow { NotFoundException("Place with id $placeId not found") }

        if (place.owners.isNotEmpty()) {
            if (place.owners.contains(personId)) {
                throw DuplicateException("Person already owns this place")
            }
        }

        if (person.role == Role.USER) {
            person.role = Role.OWNER
        }

        person.placesOwned += placeId
        place.owners = listOf(personId)

        personRepository.save(person)
        placeRepository.save(place)

        return PersonWithPlacesResponse.fromEntity(person, place)
    }

    @Transactional
    fun deletePersonById(personId: String) {
        val personToDelete = personRepository.findById(personId)
            .orElseThrow { NotFoundException("Person with id $personId not found") }

        personRepository.delete(personToDelete)
        placeRepository.deleteAllByOwnersContains(personId)
    }
}