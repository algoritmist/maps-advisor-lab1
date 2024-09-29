package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("favorites")
data class FavoriteEntity(
    @Id
    val id: String? = null,

    @Field("person_id")
    var personId: String,

    @Field("place_id")
    var placeId: String,

    var favorites: Favorite
)

enum class Favorite {
    HOME,
    WORK,
    ENTERTAINMENT,
}