import org.mapsAdvisor.mapsAdvisor.MapsAdvisorApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(classes = [MapsAdvisorApplication::class])
abstract class AbstractBaseIntegrationTest {
    companion object {
        @Container
        var mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:latest").withExposedPorts(27017)

        @JvmStatic
        @DynamicPropertySource
        fun containersProperties(registry: DynamicPropertyRegistry) {
            mongoDBContainer.start()
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
        }
    }

}