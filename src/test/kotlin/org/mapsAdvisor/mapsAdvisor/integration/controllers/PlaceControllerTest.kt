package org.mapsAdvisor.mapsAdvisor.integration.controllers

import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.integration.IntegrationEnvironment
import org.mapsAdvisor.mapsAdvisor.model.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.model.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.model.request.UpdateDescriptionRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class PlaceControllerTest : IntegrationEnvironment() {
    @Autowired
    private lateinit var mvc: MockMvc
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun givenPlace_whenSave_thenGetPlace() {
        val mvcResult = mvc.perform(
            post("/api/v1/place").contentType("application/json")
                .content(
                    objectMapper.writeValueAsString(
                        CreatePlaceRequest(
                            "Banana",
                            Coordinates(4.7, 8.0),
                            listOf(),
                            listOf(),
                            "aboba"
                        )
                    )
                )
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(mvcResult.response.contentAsString).get("id").asText()

        mvc.perform(get("/api/v1/place/$id"))
            .andExpect(status().isOk())
    }

    @Test
    fun givenPlace_whenUpdateDescription_thenGetUpdatedPlace() {
        val createPlaceRequest = CreatePlaceRequest("Banana", Coordinates(6.6, 8.0), listOf(), listOf(), "aboba")

        val mvcResult = mvc.perform(
            post("/api/v1/place")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createPlaceRequest))
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(mvcResult.response.contentAsString).get("id").asText()

        val updateDescriptionRequest = UpdateDescriptionRequest("New description")

        mvc.perform(
            patch("/api/v1/place/$id/description")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateDescriptionRequest))
        )
            .andExpect(status().isOk)

        mvc.perform(get("/api/v1/place/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.description").value("New description"))
    }

    @Test
    fun givenPlace_whenDelete_thenPlaceNotFound() {
        val createPlaceRequest = CreatePlaceRequest(
            "Banana",
            Coordinates(4.7, 8.9),
            listOf(),
            listOf(),
            "aboba"
        )

        val mvcResult = mvc.perform(
            post("/api/v1/place")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createPlaceRequest))
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(mvcResult.response.contentAsString).get("id").asText()

        mvc.perform(delete("/api/v1/place/$id"))
            .andExpect(status().isNoContent)


        mvc.perform(get("/api/v1/place/$id"))
            .andExpect(status().isNotFound)
    }


}