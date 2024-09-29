package org.mapsAdvisor.mapsAdvisor.service

import io.quarkus.liquibase.mongodb.LiquibaseMongodbFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import liquibase.changelog.ChangeSetStatus

@ApplicationScoped
class MigrationService {
    // You can Inject the object if you want to use it manually
    @Inject
    lateinit var liquibaseMongodbFactory: LiquibaseMongodbFactory

    fun checkMigration() {
        // Use the liquibase instance manually
        liquibaseMongodbFactory.createLiquibase().use { liquibase ->
            liquibase.dropAll()
            liquibase.validate()
            liquibase.update(liquibaseMongodbFactory.createContexts(), liquibaseMongodbFactory.createLabels())
            // Get the list of liquibase change set statuses
            val status: List<ChangeSetStatus> =
                liquibase.getChangeSetStatuses(liquibaseMongodbFactory.createContexts(), liquibaseMongodbFactory.createLabels())
        }
    }
}