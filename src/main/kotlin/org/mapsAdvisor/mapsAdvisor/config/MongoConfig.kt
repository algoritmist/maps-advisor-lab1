package org.mapsAdvisor.mapsAdvisor.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@PropertySource("classpath:mongo.properties")
@EnableMongoRepositories(basePackages = ["org.mapsAdvisor.mapsAdvisor.repository"])
@ComponentScan(basePackages = ["org.mapsAdvisor.mapsAdvisor.service"])
class MongoConfig : AbstractMongoClientConfiguration() {
    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory?): MongoTransactionManager {
        return MongoTransactionManager(dbFactory!!)
    }

    @Value("\${mongo.url}")
    private val url: String? = null

    @Value("\${mongo.db}")
    private val database: String? = null

    @Value("\${mongo.user}")
    private val user: String? = null

    @Value("\${mongo.password}")
    private val password: String? = null

    @Value("\${mongo.authSource}")
    private val authSource: String? = null

    override fun getMappingBasePackages() = mutableSetOf("org.mapsAdvisor.mapsAdvisor.entity")

    override fun getDatabaseName(): String = database!!

    override fun autoIndexCreation(): Boolean {
        return true
    }

    override fun mongoClientSettings(): MongoClientSettings {
        val builder = MongoClientSettings.builder()
        builder.applyConnectionString(ConnectionString(url!!))
            .credential(
                MongoCredential.createScramSha1Credential(
                    user!!,
                    authSource!!,
                    password!!.toCharArray()))
        this.configureClientSettings(builder)
        return builder.build()
    }
}