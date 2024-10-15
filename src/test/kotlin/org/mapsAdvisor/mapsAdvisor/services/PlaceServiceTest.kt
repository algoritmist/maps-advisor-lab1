package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mapsAdvisor.mapsAdvisor.entity.Place
import org.mapsAdvisor.mapsAdvisor.exception.NotFoundException
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.request.Coordinates
import org.mapsAdvisor.mapsAdvisor.request.CreatePlaceRequest
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.geo.Point
import java.util.*
import kotlin.test.assertEquals

class PlaceServiceTest {
    private val placeRepository = mock<PlaceRepository>()
    private val placeFeedbackRepository = mock<PlaceFeedbackRepository>()
    private val personRepository = mock<PersonRepository>()
    private val favoritesRepository = mock<FavoritesRepository>()
    private val placeService = PlaceService(placeRepository, placeFeedbackRepository, personRepository, favoritesRepository)

    @Test
    fun `create place should create place`(){
        /*val personID = UUID.randomUUID().toString()
        assertDoesNotThrow { placeService.createPlace(CreatePlaceRequest(
            name = UUID.randomUUID().toString(),
            coordinates = Coordinates(0.0, 0.0),
            tags = listOf(),
            owners = listOf(),
            info = ""
        )) }*/
    }

    @Test
    fun `create place should throw NotFoundException`(){

    }

    @Test
    fun `create place should throw IllegalArgumentException if already added`(){}

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `find all should return page`(pageSize: Int){
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<List<Place>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(placeRepository.findAll(pageable)).thenReturn(mock<Page<Place>>())
        whenever(placeRepository.findAll(pageable).content).thenReturn(list)
        val listGot = placeService.findAll(0, pageSize)
        assertEquals(list.size, listGot.size)
    }

    @Test
    fun `find by id should return place`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.of(mock<Place>()))
        assertDoesNotThrow { placeService.findById(placeId) }
    }

    @Test
    fun `find by id should return NotFoundException`(){
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.empty())
        assertThrows<NotFoundException> { placeService.findById(placeId)  }
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `find by location near should return page`(pageSize: Int){
        val latitude = 10.0
        val longitude = 20.0
        val distance = 15.0
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<List<Place>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(placeRepository.findByCoordinatesNear(Point(latitude, longitude), pageable)).thenReturn(mock<Page<Place>>())
        whenever(placeRepository.findByCoordinatesNear(Point(latitude, longitude), pageable).content).thenReturn(list)
        val listGot = placeService.findByLocationNear(latitude, longitude, distance, 0, pageSize)
        assertEquals(list.size, listGot.size)
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun  `find by places with tag should return page`(pageSize: Int){
        val latitude = 10.0
        val longitude = 20.0
        val distance = 15.0
        val tag = UUID.randomUUID().toString()
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<List<Place>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(placeRepository.findByCoordinatesNearAndTagsContains(Point(latitude, longitude), tag, pageable)).thenReturn(mock<Page<Place>>())
        whenever(placeRepository.findByCoordinatesNearAndTagsContains(Point(latitude, longitude), tag, pageable).content).thenReturn(list)
        val listGot = placeService.findNearbyPlacesWithTag(latitude, longitude, distance, tag, 0, pageSize)
        assertEquals(list.size, listGot.size)

    }

    @ParameterizedTest
    @ValueSource(ints = [10, 20, 50])
    fun `find nearby places by name should return page`(pageSize: Int){
        /*val latitude = 10.0
        val longitude = 20.0
        val distance = 15.0
        val name = UUID.randomUUID().toString()
        val pageable = PageRequest.of(0, pageSize)
        val list = mock<List<Place>>()
        whenever(list.size).thenReturn(pageSize)
        whenever(placeRepository.findByCoordinatesNearAndNameContains(Point(latitude, longitude), name, pageable)).thenReturn(mock<Page<Place>>())
        whenever(placeRepository.findByCoordinatesNearAndNameContains(Point(latitude, longitude), name, pageable).content).thenReturn(list)
        val listGot = placeService.findNearbyPlacesWithTag(latitude, longitude, distance, name,0, pageSize)
        assertEquals(list.size, listGot.size)
    */
    }

    @Test
    fun `delete by id should delete place`(){
        val place = mock<Place>()
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.of(place))
        assertDoesNotThrow { placeService.deleteById(placeId) }
    }

    @Test
    fun `delete by id should throw NotFoundException`(){
        /*val place = mock<Place>()
        val placeId = UUID.randomUUID().toString()
        whenever(placeRepository.findById(placeId)).thenReturn(Optional.empty())
        assertDoesNotThrow { placeService.deleteById(placeId) }*/
    }

    @Test
    fun `count all should count`(){
        whenever(placeRepository.count()).thenReturn(42)
        //assertEquals(placeService.countAll(), 42)
    }
}