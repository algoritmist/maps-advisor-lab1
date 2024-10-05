package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.entity.Person
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonRepository : MongoRepository<Person, String> {
    fun findAllByPlacesOwnedContains(placeId: String): List<Person>
    fun existsByUsername(username: String): Boolean
}