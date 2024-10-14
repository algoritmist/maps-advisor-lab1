package org.mapsAdvisor.mapsAdvisor.services

import org.junit.jupiter.api.Test

class FeedbackServiceTest {
    @Test
    fun `test createRouteFeedback successfully creates route feedback`(){}

    @Test
    fun `test createRouteFeedback throws NotFoundException when route not found`(){}

    @Test
    fun `test createRouteFeedback throws NotFoundException when person not found`(){}

    @Test
    fun `test getRouteFeedbacks returns paginated route feedbacks`(){}

    @Test
    fun `test getRouteFeedbacks throws NotFoundException when route not found`(){}

    @Test
    fun `test deleteRouteFeedback successfully deletes feedback`(){}

    @Test
    fun `test deleteRouteFeedback throws NotFoundException when feedback does not exist`(){}

    @Test
    fun `test createPlaceFeedback successfully creates place feedback`(){}

    @Test
    fun `test createPlaceFeedback throws NotFoundException when place not found`(){}

    @Test
    fun `test createPlaceFeedback throws NotFoundException when person not found`(){}

    @Test
    fun `test getPlaceFeedbacks returns paginated place feedbacks`(){}

    @Test
    fun `test getPlaceFeedbacks throws NotFoundException when place not found`(){}

    @Test
    fun `test deletePlaceFeedback successfully deletes feedback`(){}

    @Test
    fun `test deletePlaceFeedback throws NotFoundException when feedback does not exist`(){}
}