package org.mapsAdvisor.mapsAdvisor.configs

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.transaction.TransactionProperties
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.Duration

@SpringBootTest(
    classes = [
        TransactionProperties::class,
        ServerProperties::class,
    ]
)
@EnableConfigurationProperties(
    TransactionProperties::class,
    ServerProperties::class
)
class ConfigsTest {
    @Autowired
    private lateinit var transactionProperties: TransactionProperties

    @Autowired
    private lateinit var serverProperties: ServerProperties

    @Test
    fun `test set configs`() {
        //assertEquals(Duration.ofSeconds(5), transactionProperties.defaultTimeout)
        assertEquals(8080, serverProperties.port)
    }
}