package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test

class FavoritesServiceTest {

    @Test
    fun `test saveFavorite with valid data`(){}

    @Test
    fun `test saveFavorite throws NotFoundException when place not found`(){}

    @Test
    fun `test saveFavorite throws NotFoundException when person not found`(){}

    @Test
    fun `test getFavoriteById returns favorite`(){}

    @Test
    fun `test getFavoriteById throws NotFoundException`(){}

    @Test
    fun `test getFavoritesByPersonId returns list of favorites`(){}

    @Test
    fun `test getFavoritesByPersonId throws NotFoundException when person not found`(){}

    @Test
    fun `test deleteFavorite calls repository delete`(){}

    @Test
    fun `test deleteFavorite throws NotFoundException`(){}
}