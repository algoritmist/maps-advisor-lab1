package org.mapsAdvisor.mapsAdvisor.integration.controllers

import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.model.request.CreateRouteRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest
class RouteControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Test
    fun givenRoute_whenCreate_thenGetCreatedRoute() {
        val createRouteRequest = CreateRouteRequest("Test Route", "aboba", listOf("place1", "place2"))

        val mvcResult = mvc.perform(
            post("/api/v1/route")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createRouteRequest))
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = objectMapper.readTree(mvcResult.response.contentAsString).get("id").asText()

        mvc.perform(get("/api/v1/route/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.name").value("Test Route"))
    }

    @Test
    fun givenRoute_whenDelete_thenRouteNotFound() {
        val createRouteRequest = CreateRouteRequest("Banana", "aboba", listOf("place1", "place2"))

        val mvcResult = mvc.perform(
            post("/api/v1/route")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createRouteRequest))
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(mvcResult.response.contentAsString).get("id").asText()

        mvc.perform(delete("/api/v1/route/$id"))
            .andExpect(status().isNoContent)


        mvc.perform(get("/api/v1/route/$id"))
            .andExpect(status().isNotFound)
    }
}