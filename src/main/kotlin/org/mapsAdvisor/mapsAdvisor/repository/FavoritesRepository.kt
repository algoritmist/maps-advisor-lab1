package org.mapsAdvisor.mapsAdvisor.repository

import org.mapsAdvisor.mapsAdvisor.model.Place
import org.springframework.data.mongodb.repository.MongoRepository

interface FavoritesRepository : MongoRepository<Place, String>