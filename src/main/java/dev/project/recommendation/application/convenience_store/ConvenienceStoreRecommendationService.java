package dev.project.recommendation.application.convenience_store;

import dev.project.recommendation.application.Base62Service;
import dev.project.recommendation.application.direction.DirectionService;
import dev.project.recommendation.application.dto.OutputDto;
import dev.project.recommendation.infra.search.kakao_api.KakaoAddressSearchExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConvenienceStoreRecommendationService {

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final KakaoAddressSearchExecutor kakaoAddressSearchExecutor;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    @Value("${recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendConvenienceStoreList(String address) {

        var kakaoApiResponseDto = kakaoAddressSearchExecutor.requestAddressSearch(address);

        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[ConvenienceStoreRecommendationService.recommendConvenienceStoreList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        var documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        // 1. db 에 저장된 정적 편의점 데이터를 사용한 검색 리스트 조회
//        var directionList = directionService.buildDirectionList(documentDto.getAddressName(), documentDto.getLatitude(), documentDto.getLongitude());

        // 2. kakao api 의 '편의점' 카테고리 검색 api 를 사용한 검색 리스트 조회
        var directionList
                = directionService.buildDirectionListByApi(documentDto.getAddressName(), documentDto.getLatitude(), documentDto.getLongitude());


        return directionService.storeAll(directionList)
                .stream()
                .map(directionDto -> {
                    var directionUrl = baseUrl + base62Service.encodeDirectionId(directionDto.getId()); // 길찾기 shorten url 생성
                    var roadViewUrl = ROAD_VIEW_BASE_URL + directionDto.getTargetLatitude() + "," + directionDto.getTargetLongitude(); // 로드뷰 url 생성
                    return OutputDto.of(directionDto, directionUrl, roadViewUrl); // OutputDto 로 변환
                })
                .collect(Collectors.toList());
    }
}
