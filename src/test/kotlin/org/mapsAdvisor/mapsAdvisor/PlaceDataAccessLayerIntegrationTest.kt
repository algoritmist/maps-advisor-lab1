package org.mapsAdvisor.mapsAdvisor

import AbstractBaseIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.util.*


class PlaceDataLayerAccessIntegrationTest : AbstractBaseIntegrationTest() {
    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Test
    fun givenPlaceRepository_whenSaveAndRetrieveProduct_thenOK() {
        val product = Place("123", "1L Milk", GeoJsonPoint(10.0, 10.0))

        val createdProduct: Place = placeRepository.save(product)
        val optionalProduct: Optional<Place> = placeRepository.findById(createdProduct.id!!)

        assertThat(optionalProduct.isPresent).isTrue()

        val retrievedProduct: Place = optionalProduct.get()
        assertThat(retrievedProduct.id).isEqualTo(product.id)
    }

    @Test
    fun givenPlaceRepository_whenFindByName_thenOK() {
        val product = Place("456", "Fruit", GeoJsonPoint(10.0, 10.0))

        val createdProduct: Place = placeRepository.save(product)
        val optionalProduct: Page<Place> = placeRepository.findAllByCoordinates(createdProduct.coordinates.x, createdProduct.coordinates.y, PageRequest.of(0, 50))

        assertThat(optionalProduct.get().findFirst().isPresent).isTrue()

        val retrievedProduct: Place = optionalProduct.get().findFirst().get()
        assertThat(retrievedProduct.id).isEqualTo(product.id)
    }
}