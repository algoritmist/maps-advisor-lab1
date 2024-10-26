package org.mapsAdvisor.mapsAdvisor.integration.repo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.integration.IntegrationEnvironment
import org.mapsAdvisor.mapsAdvisor.model.entity.Place
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import java.util.*

class PlaceRepositoryIntegrationTest: IntegrationEnvironment() {

    @Autowired
    private lateinit var placeRepository: PlaceRepository

    @Test
    fun givenPlaceRepository_whenSaveAndRetrievePlace_thenOK() {
        val product = Place("123", "Gym", GeoJsonPoint(10.0, 10.0))

        val createdProduct: Place = placeRepository.save(product)
        val optionalProduct: Optional<Place> = placeRepository.findById(createdProduct.id!!)

        assertThat(optionalProduct.isPresent).isTrue()

        val retrievedProduct: Place = optionalProduct.get()
        assertThat(retrievedProduct.id).isEqualTo(product.id)
    }

    @Test
    fun givenPlaceRepository_whenFindAllWithPagination_thenOK() {
        val place1 = Place("124", "Library", GeoJsonPoint(15.0, 15.0))
        val place2 = Place("125", "Museum", GeoJsonPoint(20.0, 20.0))
        placeRepository.saveAll(listOf(place1, place2))

        val pageable: Pageable = PageRequest.of(0, 50)
        val places = placeRepository.findAll(pageable)

        assertThat(places.content.size).isGreaterThanOrEqualTo(2)
    }

    @Test
    fun givenPlaceRepository_whenFindByCoordinatesNear_thenOK() {
        val place = Place("126", "Cafe", GeoJsonPoint(25.0, 25.0))
        placeRepository.save(place)

        val pageable: Pageable = PageRequest.of(0, 10)
        val foundPlaces = placeRepository.findByCoordinatesNear(Point(25.0, 25.0), pageable)

        assertThat(foundPlaces.content).isNotEmpty
        assertThat(foundPlaces.content[0].name).isEqualTo("Cafe")
    }

    @Test
    fun givenPlaceRepository_whenFindByCoordinatesNearAndTagsContains_thenOK() {
        val place = Place("127", "Park", GeoJsonPoint(30.0, 30.0), tags = listOf("Nature"))
        placeRepository.save(place)

        val pageable: Pageable = PageRequest.of(0, 10)
        val foundPlaces = placeRepository.findByCoordinatesNearAndTagsContains(Point(30.0, 30.0), "Nature", pageable)

        assertThat(foundPlaces.content).isNotEmpty
        assertThat(foundPlaces.content[0].name).isEqualTo("Park")
    }

    @Test
    fun givenPlaceRepository_whenFindByCoordinatesNearAndNameContains_thenOK() {
        val place = Place("128", "Beach", GeoJsonPoint(35.0, 35.0))
        placeRepository.save(place)

        val pageable: Pageable = PageRequest.of(0, 10)
        val foundPlaces = placeRepository.findByCoordinatesNearAndNameContains(Point(35.0, 35.0), "Bea", pageable)

        assertThat(foundPlaces.content).isNotEmpty
        assertThat(foundPlaces.content[0].name).isEqualTo("Beach")
    }

    @Test
    fun givenPlaceRepository_whenFindByCoordinates_thenOK() {
        val place = Place("129", "Zoo", GeoJsonPoint(40.0, 40.0))
        placeRepository.save(place)

        val foundPlace = placeRepository.findByCoordinates(GeoJsonPoint(40.0, 40.0))

        assertThat(foundPlace.isPresent).isTrue()
        assertThat(foundPlace.get().name).isEqualTo("Zoo")
    }

    @Test
    fun givenPlaceRepository_whenDeleteAllByOwnersContains_thenOK() {
        val place1 = Place("130", "Aquarium", GeoJsonPoint(45.0, 45.0), owners = listOf("owner1"))
        val place2 = Place("131", "Botanical Garden", GeoJsonPoint(50.0, 50.0), owners = listOf("owner1", "owner2"))
        placeRepository.saveAll(listOf(place1, place2))

        placeRepository.deleteAllByOwnersContains("owner1")

        val remainingPlaces = placeRepository.findAll()
        assertThat(remainingPlaces).doesNotContain(place1, place2)
    }

    @Test
    fun givenPlaceRepository_whenDeletePlace_thenNotFound() {
        val place = Place("132", "Theater", GeoJsonPoint(55.0, 55.0))
        val savedPlace: Place = placeRepository.save(place)

        placeRepository.delete(savedPlace)

        val deletedPlace: Optional<Place> = placeRepository.findById(savedPlace.id!!)
        assertThat(deletedPlace.isPresent).isFalse
    }
}