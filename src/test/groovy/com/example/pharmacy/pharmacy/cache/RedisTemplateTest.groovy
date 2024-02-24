package com.example.pharmacy.pharmacy.cache

import com.example.pharmacy.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations

/*
통합테스트이기 때문에 모든 빈을 조회, 환경변수 추가 필수!
 */
class RedisTemplateTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    RedisTemplate redisTemplate;

    def "RedisTemplate String Operations"() {
        given:
        def valueOperations = redisTemplate.opsForValue()
        def key = "stringKey"
        def value = "seungseok"

        when:
        valueOperations.set(key, value)

        then:
        def result = valueOperations.get(key)
        result == value
    }

    def "RedisTemplate set Operations"() {
        given:
        def setOperations = redisTemplate.opsForSet()
        def key = "setKey"

        when:
        setOperations.add(key, "h","e","l","l","o")

        // Redis 의 Set 자료구조는 중복을 허용하지 않는다.
        then:
        def size = setOperations.size(key)
        size == 4
    }
    
    def "RedisTemplate hash Operations"() {
        given:
        def hashOperations = redisTemplate.opsForHash()
        def key = "hashKey"

        when:
        hashOperations.put(key, "subKey", "value")

        then:
        def result = hashOperations.get(key, "subKey")
        result == "value"

        // Map 형태의 Collection
        def entries = hashOperations.entries(key)
        entries.keySet().contains("subKey")
        entries.values().contains("value")

        def size = hashOperations.size(key)
        size == entries.size()
    }
    
}
