package dev.project.recommendation.application.direction

import dev.project.recommendation.application.Base62Service
import dev.project.recommendation.application.convenience_store.ConvenienceStoreService
import dev.project.recommendation.application.dto.ConvenienceStoreDto
import dev.project.recommendation.domain.direction.DirectionReader
import dev.project.recommendation.domain.direction.DirectionStore
import dev.project.recommendation.domain.search.StoreSearchApiService
import dev.project.recommendation.infra.search.kakao_api.dto.response.AddressDocumentDto
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private ConvenienceStoreService convenienceStoreSearchService = Mock()
    private StoreSearchApiService storeSearchApiService = Mock()
    private DirectionReader directionReader = Mock()
    private DirectionStore directionStore = Mock()
    private Base62Service base62Service = Mock()

    private DirectionService directionService = new DirectionService(directionStore, directionReader,
            convenienceStoreSearchService, base62Service, storeSearchApiService)

    private List<ConvenienceStoreDto> storeDtoList

    def setup() {
        storeDtoList = new ArrayList<>()

        storeDtoList.add(
                ConvenienceStoreDto.builder()
                .storeName("CU 잠실갤러리아점")
                .storeAddress("주소1")
                .latitude(37.511583)
                .longitude(127.094223)
                .build())

        storeDtoList.add(
                ConvenienceStoreDto.builder()
                        .storeName("세븐일레븐 잠실캐슬골드점")
                        .storeAddress("주소2")
                        .latitude(37.514614)
                        .longitude(127.099999)
                        .build())
    }

    def "buildDirectionList - 결과값이 거리순으로 정렬되는지 확인" () {
        given:
        def addressName = "서울특별시 송파구 올림픽로 240"
        double inputLatitude = 37.5113096112036
        double inputLongitude = 127.098141751177

        def documentDto = AddressDocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        convenienceStoreSearchService.searchConvenienceStoreDtoList() >> storeDtoList

        def result = directionService.buildDirectionList(documentDto.getAddressName(),
                documentDto.getLatitude(), documentDto.getLongitude())

        then:
        result.size() == 2
        result.get(0).getDistance() <= result.get(1).getDistance()
    }

    def "buildDirectionList - 정해진 반경 3km 내에서 검색이 되는지 확인" () {
        given:
        storeDtoList.add(
                ConvenienceStoreDto.builder()
                        .storeName("GS25 삼성점")
                        .storeAddress("주소3")
                        .latitude(37.507870)
                        .longitude(127.060725)
                        .build()
        )
        def addressName = "서울특별시 송파구 올림픽로 240"
        double inputLatitude = 37.5113096112036
        double inputLongitude = 127.098141751177

        def documentDto = AddressDocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        convenienceStoreSearchService.searchConvenienceStoreDtoList() >> storeDtoList

        def result = directionService.buildDirectionList(documentDto.getAddressName(),
                documentDto.getLatitude(), documentDto.getLongitude())

        then:
        result.size() == 2
        result.get(0).getDistance() <= result.get(1).getDistance()
    }
}
