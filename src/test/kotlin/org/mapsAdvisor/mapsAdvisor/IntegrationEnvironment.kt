import org.mapsAdvisor.mapsAdvisor.MapsAdvisorApplication
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration

@Testcontainers
@SpringBootTest(classes = [MapsAdvisorApplication::class])
abstract class IntegrationEnvironment {

    companion object {
        @Container
        val mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:latest").withExposedPorts(27017)
            .waitingFor(Wait.forListeningPort())
            .withStartupTimeout(Duration.ofSeconds(60))

        @JvmStatic
        @DynamicPropertySource
        fun containerProperties(registry: DynamicPropertyRegistry) {

            mongoDBContainer.start()

            registry.add("spring.data.mongodb.host") { mongoDBContainer.host }
            registry.add("spring.data.mongodb.port") { mongoDBContainer.firstMappedPort }
        }
    }
}
