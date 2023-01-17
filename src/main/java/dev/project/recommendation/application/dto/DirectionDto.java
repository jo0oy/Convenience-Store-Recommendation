package dev.project.recommendation.application.dto;

import dev.project.recommendation.domain.direction.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDto {
    private Long id;

    // 고객 주소 정보
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 추천 편의점 정보
    private String targetStoreName;
    private String targetStoreUrl;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 고객 주소와 추천 편의점간의 거리
    private double distance;

    public static DirectionDto fromEntity(Direction entity) {
        return DirectionDto.builder()
                .id(entity.getId())
                .inputAddress(entity.getInputAddress())
                .inputLatitude(entity.getInputLatitude())
                .inputLongitude(entity.getInputLongitude())
                .targetStoreName(entity.getTargetStoreName())
                .targetStoreUrl(entity.getTargetStoreUrl())
                .targetAddress(entity.getTargetAddress())
                .targetLatitude(entity.getTargetLatitude())
                .targetLongitude(entity.getTargetLongitude())
                .distance(entity.getDistance())
                .build();
    }
}
