package dev.project.recommendation.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class OutputDto {

    private String storeName;    // 편의점 명
    private String storeAddress; // 편의점 주소
    private String placeUrl; // 편의점 상세 url
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 편의점 주소의 거리v

    public static OutputDto of(DirectionDto directionDto,
                               String directionUrl,
                               String roadViewUrl) {
        return OutputDto.builder()
                .storeName(directionDto.getTargetStoreName())
                .storeAddress(directionDto.getTargetAddress())
                .placeUrl(directionDto.getTargetStoreUrl())
                .directionUrl(directionUrl)
                .roadViewUrl(roadViewUrl)
                .distance(String.format("%.2f km", directionDto.getDistance()))
                .build();
    }
}
