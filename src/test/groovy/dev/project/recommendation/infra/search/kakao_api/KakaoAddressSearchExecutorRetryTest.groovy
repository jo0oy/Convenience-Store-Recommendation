package dev.project.recommendation.infra.search.kakao_api

import com.fasterxml.jackson.databind.ObjectMapper
import dev.project.recommendation.AbstractIntegrationContainerBaseTest
import dev.project.recommendation.infra.search.kakao_api.dto.response.AddressDocumentDto
import dev.project.recommendation.infra.search.kakao_api.dto.response.KakaoAddressApiResponseDto
import dev.project.recommendation.infra.search.kakao_api.dto.response.MetaDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class KakaoAddressSearchExecutorRetryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchExecutor kakaoAddressSearchExecutor

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()

    def setup(){
        mockWebServer = new MockWebServer()
        mockWebServer.start()
    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry 성공"() {
        given:
        String inputAddress = "서울특별시 송파구 올림픽로 240"

        def metaDto = new MetaDto(1)
        def documentDto = AddressDocumentDto.builder()
                .addressName(inputAddress).build()
        def expectedResponse = new KakaoAddressApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectedResponse)))

        def kakaoApiResult = kakaoAddressSearchExecutor.requestAddressSearch(inputAddress)
        def request = mockWebServer.takeRequest()

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        request.getMethod() == "GET"
        kakaoApiResult.getDocumentList().size() == 1
        kakaoApiResult.getMetaDto().getTotalCount() == 1
        kakaoApiResult.getDocumentList().get(0).getAddressName() == inputAddress
    }

    def "requestAddressSearch retry 실패" () {
        given:
        String inputAddress = "서울특별시 송파구 올림픽로 240"
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def kakaoApiResult = kakaoAddressSearchExecutor.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        kakaoApiResult == null
    }
}
