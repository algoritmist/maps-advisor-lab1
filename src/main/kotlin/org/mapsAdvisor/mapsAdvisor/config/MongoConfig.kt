package org.mapsAdvisor.mapsAdvisor.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(basePackages = ["org.mapsAdvisor.mapsAdvisor.repository"])
@ComponentScan(basePackages = ["org.mapsAdvisor.mapsAdvisor.service"])
class MongoConfig : AbstractMongoClientConfiguration() {
    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory?): MongoTransactionManager {
        return MongoTransactionManager(dbFactory!!)
    }

    @Value("\${app.datasource.url}")
    private val url: String? = null

    @Value("\${app.datasource.db}")
    private val database: String? = null

    @Value("\${app.datasource.username}")
    private val user: String? = null

    @Value("\${app.datasource.password}")
    private val password: String? = null

    @Value("\${app.datasource.authSource}")
    private val authSource: String? = null

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