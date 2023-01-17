package dev.project.recommendation.infra.convenience_store

import dev.project.recommendation.AbstractIntegrationContainerBaseTest
import dev.project.recommendation.domain.convenience_store.ConvenienceStore
import org.springframework.beans.factory.annotation.Autowired

class ConvenienceStoreRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ConvenienceStoreRepository convenienceStoreRepository

    def setup() {
        convenienceStoreRepository.deleteAll()
    }

    def "convenienceStoreRepository - save"() {
        given:
        def address = "서울특별시 송파구 가락동"
        def storeName = "GS25 가락점"
        double latitude = 37.12
        double longitude = 127.12

        def store= ConvenienceStore.builder()
                .storeAddress(address)
                .storeName(storeName)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = convenienceStoreRepository.save(store)

        then:
        result.getStoreAddress() == address
        result.getStoreName() == storeName
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

    def "convenienceStoreRepository - saveAll"() {
        given:
        String address = "서울특별시 송파구 가락동"
        String storeName = "GS25 가락점"
        double latitude = 37.12
        double longitude = 127.12

        def store= ConvenienceStore.builder()
                .storeAddress(address)
                .storeName(storeName)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        convenienceStoreRepository.saveAll(Arrays.asList(store))
        def result = convenienceStoreRepository.findAll()

        then:
        result.size() == 1
    }
}
