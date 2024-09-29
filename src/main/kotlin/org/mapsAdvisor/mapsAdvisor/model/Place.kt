package org.mapsAdvisor.mapsAdvisor.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("places")
data class Place(
    @Id
    val id: String? = null,
    var name: String,
    @GeoSpatialIndexed(name = "coordinates", type = GeoSpatialIndexType.GEO_2DSPHERE)
    var coordinates: GeoJsonPoint,
    var tags: List<String> = listOf(),
    var owners: List<String> = listOf(),
    var info: String? = null
)