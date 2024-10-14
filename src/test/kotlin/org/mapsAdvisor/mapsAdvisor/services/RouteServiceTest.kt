package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test

class RouteServiceTest {
    @Test
    fun `test createRoute successfully creates a route`(){}

    @Test
    fun `test createRoute throws IllegalStateException on DataAccessException`(){}

    @Test
    fun `test findAll returns paginated list of routes`(){}

    @Test
    fun `test findById returns a route if found`(){}

    @Test
    fun `test findById throws NotFoundException if route not found`(){}

    @Test
    fun `test deleteById successfully deletes route and associated feedback`(){}

    @Test
    fun `test deleteById throws NotFoundException if route does not exist`(){}

    @Test
    fun `test deleteById throws IllegalStateException on DataAccessException`(){}

    @Test
    fun `test findRoutesByPlaceId returns list of routes containing the place`(){}
}