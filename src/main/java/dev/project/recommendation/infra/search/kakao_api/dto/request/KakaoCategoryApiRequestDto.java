package dev.project.recommendation.infra.search.kakao_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoCategoryApiRequestDto {
    private double latitude;
    private double longitude;
    private double radius;

    public static KakaoCategoryApiRequestDto of(double latitude, double longitude, double radius) {
        return new KakaoCategoryApiRequestDto(latitude, longitude, radius);
    }
}
