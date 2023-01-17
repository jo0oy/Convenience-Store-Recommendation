package dev.project.recommendation.application.direction;

import dev.project.recommendation.application.Base62Service;
import dev.project.recommendation.application.convenience_store.ConvenienceStoreService;
import dev.project.recommendation.application.dto.DirectionDto;
import dev.project.recommendation.domain.direction.Direction;
import dev.project.recommendation.domain.direction.DirectionReader;
import dev.project.recommendation.domain.direction.DirectionStore;
import dev.project.recommendation.domain.search.StoreSearchApiService;
import dev.project.recommendation.infra.search.kakao_api.dto.request.KakaoCategoryApiRequestDto;
import dev.project.recommendation.infra.search.kakao_api.dto.response.KakaoCategoryApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 5; // 최대 검색 갯수
    private static final double RADIUS_KM = 3.0; // 반경 3 km
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final DirectionStore directionStore;
    private final DirectionReader directionReader;
    private final ConvenienceStoreService convenienceStoreService;
    private final Base62Service base62Service;
    private final StoreSearchApiService<?> storeSearchApiService;

    @Transactional
    public List<DirectionDto> storeAll(List<Direction> directionList) {
        return directionStore.storeAll(directionList)
                .stream()
                .map(DirectionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public String findDirectionUrlByEncodedId(String encodedId) {

        Long decodedId = base62Service.decodeDirectionId(encodedId);
        Direction direction = directionReader.findDirection(decodedId);

        var params = String.join(",", direction.getTargetStoreName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));

        return UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + params).toUriString();
    }

    public List<Direction> buildDirectionList(String addressName, Double latitude, Double longitude) {
        if(!StringUtils.hasText(addressName) ||
                ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude)) return Collections.emptyList();

        return convenienceStoreService.searchConvenienceStoreDtoList().stream()
                .map(storeDto ->
                        Direction.builder()
                                .inputAddress(addressName)
                                .inputLatitude(latitude)
                                .targetLongitude(longitude)
                                .targetAddress(storeDto.getStoreAddress())
                                .targetStoreName(storeDto.getStoreName())
                                .targetLatitude(storeDto.getLatitude())
                                .targetLongitude(storeDto.getLongitude())
                                .distance(
                                        calculateDistance(latitude, longitude,
                                                storeDto.getLatitude(), storeDto.getLongitude())
                                ).build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<Direction> buildDirectionListByApi(String addressName, Double latitude, Double longitude) {
        if(!StringUtils.hasText(addressName) ||
                ObjectUtils.isEmpty(latitude) || ObjectUtils.isEmpty(longitude)) return Collections.emptyList();

        var apiResponseDto
                = (KakaoCategoryApiResponseDto)
                storeSearchApiService.requestSearch(KakaoCategoryApiRequestDto.of(latitude, longitude, RADIUS_KM));

        return apiResponseDto
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(addressName)
                                .inputLatitude(latitude)
                                .inputLongitude(longitude)
                                .targetStoreName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getRoadAddressName())
                                .targetStoreUrl(resultDocumentDto.getPlaceUrl())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance() * 0.001) // km 단위
                                .build())
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
