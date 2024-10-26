package org.mapsAdvisor.mapsAdvisor.integration

import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.model.entity.Person
import org.mapsAdvisor.mapsAdvisor.model.entity.Role
import java.time.Instant
import org.assertj.core.api.Assertions.assertThat

class PersonRepositoryIntegrationTest : IntegrationEnvironment() {
    @Autowired
    private lateinit var personRepository: PersonRepository

    @Test
    fun givenPersonRepository_whenFindAllByPlacesOwnedContains_thenOK() {
        val person1 = Person(
            id = "301",
            name = "a",
            username = "john_doe",
            password = "abobabab",
            role = Role.USER,
            placesOwned = listOf("place1", "place2"),
            registrationDate = Instant.now(),
        )
        val person2 = Person(
            id = "302",
            name = "a",
            username = "john_",
            password = "abobabab",
            role = Role.USER,
            placesOwned = listOf("place1", "place3"),
            registrationDate = Instant.now()
        )
        personRepository.saveAll(listOf(person1, person2))

        val foundPersons = personRepository.findAllByPlacesOwnedContains("place1")

        assertThat(foundPersons).isNotEmpty
        assertThat(foundPersons.size).isEqualTo(2)
    }

    @Test
    fun givenPersonRepository_whenExistsByUsername_thenOK() {
        val person = Person(
            id = "301",
            name = "a",
            username = "unique_user",
            password = "abobabab",
            role = Role.USER,
            placesOwned = listOf("place1", "place2"),
            registrationDate = Instant.now()
        )
        personRepository.save(person)

        val exists = personRepository.existsByUsername("unique_user")
        assertThat(exists).isTrue()

        val notExists = personRepository.existsByUsername("non_existent_user")
        assertThat(notExists).isFalse()
    }
}