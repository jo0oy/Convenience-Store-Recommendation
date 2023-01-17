package dev.project.recommendation

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {

    static final GenericContainer REDIS_CONTAINER

    static {
        REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379)

        REDIS_CONTAINER.start()

        System.setProperty("spring.redis.host", REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString())
    }
}
