package org.mapsAdvisor.mapsAdvisor.integration.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class FeedbackControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun givenManyFeedbacks_whenGetPlaceFeedbacks_thenReturnMax50Feedbacks() {
        repeat((1..55).count()) {
            val feedback = """
                                {
                                    "place_id": "671ffb5c6caad26860ae060e",
                                    "person_id": "671ffb896caad26860ae060f",
                                    "grade": 5
                                }
                                """
            mvc.perform(
                post("/api/v1/feedback/place")
                    .contentType("application/json")
                    .content(feedback)
            ).andExpect(status().isCreated)
        }

        val mvcResult = mvc.perform(
            get("/api/v1/feedback/place/671ffb5c6caad26860ae060e")
                .param("page", "0")
                .param("size", "50")
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andReturn()

        val jsonResponse = mvcResult.response.contentAsString
        val feedbackList = objectMapper.readTree(jsonResponse)

        assert(feedbackList.size() == 50)
    }
}