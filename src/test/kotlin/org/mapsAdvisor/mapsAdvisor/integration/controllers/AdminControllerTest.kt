package org.mapsAdvisor.mapsAdvisor.integration.controllers

import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.model.response.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class AdminControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun givenValidRequest_whenAssignPlace_thenReturnsUpdatedPerson() {
        val request =
            """
        {
        "person_id": "672000436caad26860ae0612",
         "place_id": "671ffb5c6caad26860ae060e"
         }
        """
        val expectedResponse =
            UserResponse(
                id = "672000436caad26860ae0612",
                username = "user1",
                role = "OWNER",
                placesOwned = listOf("671ffb5c6caad26860ae060e")
            )

        mvc.perform(
            patch("/api/v1/admin/assign")
                .contentType("application/json")
                .content(request)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(expectedResponse.id))
            .andExpect(jsonPath("$.role").value(expectedResponse.role))
    }
}