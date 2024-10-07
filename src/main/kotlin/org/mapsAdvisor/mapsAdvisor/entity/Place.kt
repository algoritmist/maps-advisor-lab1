package org.mapsAdvisor.mapsAdvisor.entity

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

typealias PersonId = String

@Document("place")
data class Place(
    @Id
    val id: String? = null,

    @NotNull
    @Size(min = 3, max = 510)
    var name: String,

    @GeoSpatialIndexed(name = "coordinates", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordinates: GeoJsonPoint,

    @Size(max = 10)
    var tags: List<String> = listOf(),
    var owners: List<PersonId> = listOf(), // references
    var info: String? = null
)