package org.mapsAdvisor.mapsAdvisor.entity

import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("place")
data class Place(
    @Id
    val id: String? = null,

    @Size(min = 1, max = 100)
    var name: String,

    @GeoSpatialIndexed(name = "coordinates", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordinates: GeoJsonPoint,

    @Size(max = 10)
    var tags: List<String> = listOf(),

    var owners: List<String> = listOf(), // references
    @Size(max = 500)
    var info: String? = null
)