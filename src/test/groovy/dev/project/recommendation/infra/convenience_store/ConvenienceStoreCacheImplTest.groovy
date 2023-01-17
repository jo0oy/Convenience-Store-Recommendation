package dev.project.recommendation.infra.convenience_store

import dev.project.recommendation.AbstractIntegrationContainerBaseTest
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCache
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCacheDto
import org.springframework.beans.factory.annotation.Autowired

class ConvenienceStoreCacheImplTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ConvenienceStoreCache convenienceStoreCache;

    def setup(){
        convenienceStoreCache.deleteAll()
    }

    def "save success" () {
        given:
        String storeName = "name"
        String storeAddress = "address"
        def storeDto =
                ConvenienceStoreCacheDto.builder()
                        .id(1L)
                        .storeName(storeName)
                        .storeAddress(storeAddress)
                        .build()

        when:
        convenienceStoreCache.save(storeDto)

        def result = convenienceStoreCache.findAll()

        then:
        result.size() == 1
        result.get(0).getId() == 1L
        result.get(0).getStoreName() == storeName
        result.get(0).getStoreAddress() == storeAddress
    }

    def "saveAll success" () {
        given:
        String storeName1 = "name1"
        String storeAddress1 = "address1"
        def store1 =
                ConvenienceStoreCacheDto.builder()
                        .id(1L)
                        .storeName(storeName1)
                        .storeAddress(storeAddress1)
                        .build()

        String storeName2 = "name2"
        String storeAddress2 = "address2"
        def store2 =
                ConvenienceStoreCacheDto.builder()
                        .id(2L)
                        .storeName(storeName2)
                        .storeAddress(storeAddress2)
                        .build()

        def list = List.of(store1, store2)

        when:
        convenienceStoreCache.saveAll(list)

        def resultList = convenienceStoreCache.findAll()

        then:
        resultList.size() == 2
    }

    def "save fail" () {
        given:
        def store = ConvenienceStoreCacheDto.builder().build()

        when:
        convenienceStoreCache.save(store)

        def result = convenienceStoreCache.findAll()

        then:
        result.size() == 0
    }

    def "delete success" () {
        given:
        String storeName = "name"
        String storeAddress = "address"
        Long storeId = 1L
        def store =
                ConvenienceStoreCacheDto.builder().id(storeId)
                        .storeName(storeName)
                        .storeAddress(storeAddress)
                        .build()

        when:
        convenienceStoreCache.save(store)

        convenienceStoreCache.delete(storeId) //  삭제 진행

        then:
        def result = convenienceStoreCache.findAll()
        result.size() == 0
    }

    def "deleteAll success" () {
        given:
        String storeName1 = "name1"
        String storeAddress1 = "address1"
        def store1 =
                ConvenienceStoreCacheDto.builder()
                        .id(1L)
                        .storeName(storeName1)
                        .storeAddress(storeAddress1)
                        .build()

        String storeName2 = "name2"
        String storeAddress2 = "address2"
        def store2 =
                ConvenienceStoreCacheDto.builder()
                        .id(2L)
                        .storeName(storeName2)
                        .storeAddress(storeAddress2)
                        .build()

        def list = List.of(store1, store2)

        when:
        convenienceStoreCache.saveAll(list)

        convenienceStoreCache.deleteAll()

        then:
        def result = convenienceStoreCache.findAll()
        result.size() == 0
    }
}
