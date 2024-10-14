package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test
import org.mapsAdvisor.mapsAdvisor.repository.FavoritesRepository
import org.mapsAdvisor.mapsAdvisor.repository.PersonRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceFeedbackRepository
import org.mapsAdvisor.mapsAdvisor.repository.PlaceRepository
import org.mapsAdvisor.mapsAdvisor.service.PlaceService
import org.mockito.Mockito.mock

class PlaceServiceTest {
    private val placeRepository = mock<PlaceRepository>()
    private val placeFeedbackRepository = mock<PlaceFeedbackRepository>()
    private val personRepository = mock<PersonRepository>()
    private val favoritesRepository = mock<FavoritesRepository>()
    private val placeService = PlaceService(placeRepository, placeFeedbackRepository, personRepository, favoritesRepository)

    @Test
    fun `create place should create place`(){}

    @Test
    fun `create place should throw NotFoundException`(){}

    @Test
    fun `create place should throw IllegalArgumentException`(){}

    @Test
    fun `find all should return page`(){}

    @Test
    fun `find all should return empty list`(){}

    @Test
    fun `find by id should return place`(){}

    @Test
    fun `find by id should return NotFoundException`(){}

    @Test
    fun `find by location near should return page`(){}

    @Test
    fun  `find by places with tag should return page`(){}

    @Test
    fun `find nearby places by name should return page`(){}

    @Test
    fun `delete by id should delete place`(){}

    @Test
    fun `delete by id should throw NotFoundException`(){}

    @Test
    fun `count all should count`(){}
}