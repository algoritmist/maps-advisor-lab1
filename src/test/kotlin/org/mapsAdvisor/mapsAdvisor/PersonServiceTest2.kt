package org.mapsAdvisor.mapsAdvisor

import IntegrationEnvironment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.util.*

class PersonServiceTest2: IntegrationEnvironment() {

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
}