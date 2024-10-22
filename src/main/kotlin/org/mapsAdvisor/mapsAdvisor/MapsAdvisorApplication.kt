package org.mapsAdvisor.mapsAdvisor

import org.mapsAdvisor.mapsAdvisor.config.MongoConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MongoConfig::class)
class MapsAdvisorApplication

fun main(args: Array<String>) {
	runApplication<MapsAdvisorApplication>(*args)
}
