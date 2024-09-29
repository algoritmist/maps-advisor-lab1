package org.mapsAdvisor.mapsAdvisor.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(basePackages = ["org.mapsAdvisor.mapsAdvisor"])
class MongoConfig : AbstractMongoClientConfiguration() {
    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory?): MongoTransactionManager {
        return MongoTransactionManager(dbFactory!!)
    }

    @Value("\${spring.data.mongodb.database}")
    private lateinit var databaseName: String

    override fun getDatabaseName(): String {
        return databaseName
    }

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString("mongodb://localhost:27017/maps")
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }
}