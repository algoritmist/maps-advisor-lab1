package org.mapsAdvisor.mapsAdvisor.service

import org.mapsAdvisor.mapsAdvisor.model.Person
import org.mapsAdvisor.mapsAdvisor.model.Role
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class PersonService(
    private val personRepository: PersonRepository,
    private val placeRepository: PlaceRepository
) {
    fun createPerson(request: PersonRequest): Person =
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

    @Transactional
    fun assignPlaceToUser(personId: String, placeId: String): Person {
        val person = personRepository.findById(personId).orElseThrow {
            throw IllegalArgumentException("Person with id $personId not found")
        }

        val place = placeRepository.findById(placeId).orElseThrow {
            throw IllegalArgumentException("Place with id $placeId not found")
        }

        if (place.owners.contains(personId)) {
            throw IllegalStateException("Person already owns this place")
        }

        if (person.role == Role.USER) {
            person.role = Role.OWNER
        }

        person.placesOwned = person.placesOwned + placeId
        place.owners = listOf(personId)

        personRepository.save(person)
        placeRepository.save(place)

        return person
    }
}