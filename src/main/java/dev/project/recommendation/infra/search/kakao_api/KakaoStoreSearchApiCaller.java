package dev.project.recommendation.infra.search.kakao_api;

import dev.project.recommendation.domain.search.StoreSearchApiService;
import dev.project.recommendation.infra.search.kakao_api.dto.request.KakaoCategoryApiRequestDto;
import dev.project.recommendation.infra.search.kakao_api.dto.response.KakaoCategoryApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoStoreSearchApiCaller implements StoreSearchApiService<KakaoCategoryApiResponseDto> {

    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;
    private static final String CONVENIENCE_STORE_CATEGORY = "CS2";

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    @Override
    public <P> KakaoCategoryApiResponseDto requestSearch(P inputDataDto) {

        var requestDto = (KakaoCategoryApiRequestDto) inputDataDto;

        var uri = kakaoUriBuilderService.buildUriByCategorySearch(requestDto.getLatitude(), requestDto.getLongitude(),
                requestDto.getRadius(), CONVENIENCE_STORE_CATEGORY);

        var headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApiKey);

        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), KakaoCategoryApiResponseDto.class).getBody();
    }

    @Recover
    public KakaoCategoryApiResponseDto recover(RuntimeException e, URI uri) {
        log.error("All the retries failed. request uri: {}, error : {}", uri.toString(), e.getMessage());
        return null;
    }
}
