package dev.project.recommendation.infra.search.kakao_api

import dev.project.recommendation.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchExecutorTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchExecutor kakaoAddressSearchExecutor

    def "address 파라메터가 null인 경우, requestAddressSearch 메서드는 null을 리턴한다"() {
        given:
        def address = null

        when:
        def result = kakaoAddressSearchExecutor.requestAddressSearch(address)

        then:
        result == null
    }

    def "address 파라메터가 valid한 경우, requestAddressSearch 메서드는 정상적으로 responseDto를 반환한다"() {
        given:
        def address = "서울특별시 송파구 올림픽로 240"

        when:
        def result = kakaoAddressSearchExecutor.requestAddressSearch(address)

        then:
        result.getDocumentList().size() > 0
        result.getMetaDto().getTotalCount() > 0
        result.getDocumentList().get(0).getAddressName() != null
    }

    def "정상적인 주소를 입력했을 경우, 정상적인 위도, 경도를 반환한다" () {
        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchExecutor.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        actualResult == expectedResult

        where:
        inputAddress                    |     expectedResult
        "서울 특별시 성북구 종암동"            |     true
        "서울 성북구 종암동 91"              |     true
        "서울 대학로"                      |     true
        "서울 성북구 종암동 잘못된 주소"        |     false
        "광진구 구의동 251-45"              |     true
        "광진구 구의동 251-455555"          |     false
        ""                              |     false
    }
}
