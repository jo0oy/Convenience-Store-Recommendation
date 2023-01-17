package dev.project.recommendation.interfaces.dto;

import dev.project.recommendation.application.dto.OutputDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class OutputResponseDto {
    private String storeName;    // 편의점 명
    private String storeAddress; // 편의점 주소
    private String placeUrl; // 편의점 상세 url
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 편의점 주소의 거리

    public static OutputResponseDto of(OutputDto outputDto) {
        return OutputResponseDto.builder()
                .storeName(outputDto.getStoreName())
                .storeAddress(outputDto.getStoreAddress())
                .placeUrl(outputDto.getPlaceUrl())
                .directionUrl(outputDto.getDirectionUrl())
                .roadViewUrl(outputDto.getRoadViewUrl())
                .distance(outputDto.getDistance())
                .build();
    }
}
