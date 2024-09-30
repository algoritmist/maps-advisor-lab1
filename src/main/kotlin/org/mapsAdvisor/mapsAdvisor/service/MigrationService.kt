package org.mapsAdvisor.mapsAdvisor.service

import io.quarkus.liquibase.mongodb.LiquibaseMongodbFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import liquibase.changelog.ChangeSetStatus
import liquibase.exception.LiquibaseException

@ApplicationScoped
class MigrationService {
    @Inject
    lateinit var liquibaseMongodbFactory: LiquibaseMongodbFactory

    fun checkMigration() {
        try {
            liquibaseMongodbFactory.createLiquibase().use { liquibase ->
                liquibase.dropAll()
                liquibase.validate()
                liquibase.update(liquibaseMongodbFactory.createContexts(), liquibaseMongodbFactory.createLabels())
                val status: List<ChangeSetStatus> = liquibase.getChangeSetStatuses(liquibaseMongodbFactory.createContexts(), liquibaseMongodbFactory.createLabels())
                println(status.size)
            }
        } catch (e: LiquibaseException) {
            throw RuntimeException(e)
        }
    }
}