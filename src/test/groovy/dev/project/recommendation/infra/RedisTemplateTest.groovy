package dev.project.recommendation.infra

import dev.project.recommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    def "RedisTemplate String operations" () {
        given:
        def valueOperations = redisTemplate.opsForValue()
        String key = "stringKey"
        String value = "hello"

        when:
        valueOperations.set(key, value)

        then:
        def result = valueOperations.get(key)

        result == value
    }

    def "RedisTemplate Set operations" () {
        given:
        def setOperations = redisTemplate.opsForSet()
        String key = "setKey"

        when:
        setOperations.add(key, "h", "e", "l", "l", "o")

        then:
        def members = setOperations.members(key)
        members.containsAll(List.of("h", "e" , "l" , "o"))
        members.size() == 4
    }

    def "RedisTemplate Hash operations" () {
        given:
        def hashOperations = redisTemplate.opsForHash()
        String key = "hashKey"

        when:
        hashOperations.put(key, "subKey", "value")

        then:
        String value = hashOperations.get(key, "subKey")
        value == "value"

        def entries = hashOperations.entries(key)
        entries.keySet().contains("subKey")
        entries.values().contains(value)

        def size = hashOperations.size(key)
        size == entries.size()
    }
}
