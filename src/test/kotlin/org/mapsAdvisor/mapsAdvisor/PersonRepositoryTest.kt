package org.mapsAdvisor.mapsAdvisor

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mapsAdvisor.mapsAdvisor.model.Role
import org.mapsAdvisor.mapsAdvisor.request.PersonRequest
import org.mapsAdvisor.mapsAdvisor.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class PersonRepositoryTest {
    @Autowired
	private lateinit var personService: PersonService

	@Test
	fun saveRepository(){
		val person = PersonRequest(
            name ="wertg",
            username = "aoiwuer",
            password = "aoiwuer",
            role = Role.USER.name,
            placesOwned = listOf()
        )
		val user = personService.createPerson(person)
		println("User ${user}")
	}

}
