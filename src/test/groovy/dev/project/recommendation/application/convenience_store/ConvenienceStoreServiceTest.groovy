package dev.project.recommendation.application.convenience_store


import dev.project.recommendation.domain.convenience_store.ConvenienceStore
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCache
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCacheDto
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreReader
import spock.lang.Specification

class ConvenienceStoreServiceTest extends Specification {

    private ConvenienceStoreService convenienceStoreService

    private ConvenienceStoreCache convenienceStoreCache = Mock()
    private ConvenienceStoreReader convenienceStoreReader = Mock()

    def setup() {
        convenienceStoreService = new ConvenienceStoreService(convenienceStoreReader, convenienceStoreCache)
    }

    def "Redis 조회 결과값이 존재하는 경우, DB 조회 하지 않는다" () {
        given:
        def cacheResult = List.of(
                ConvenienceStoreCacheDto.builder().
                        storeName("편의점1").build(),
                ConvenienceStoreCacheDto.builder().
                        storeName("편의점2").build()
        )

        when:
        convenienceStoreCache.findAll() >> cacheResult

        def resultList = convenienceStoreService.searchConvenienceStoreDtoList()

        then:
        0 * convenienceStoreReader.findAll()
        resultList.size() == 2
    }

    def "Redis 장애 혹은 Redis에 저장된 데이터가 없을 경우, DB에서 조회한다"() {
        given:
        def storeList = List.of(
                ConvenienceStore.builder()
                        .storeAddress("주소1")
                        .storeName("편의점1").build(),
                ConvenienceStore.builder()
                        .storeAddress("주소2")
                        .storeName("편의점2").build()
        )

        when:
        convenienceStoreCache.findAll() >> []
        convenienceStoreReader.findAll() >> storeList

        def result = convenienceStoreService.searchConvenienceStoreDtoList()

        then:
        result.size() == 2
    }
}
