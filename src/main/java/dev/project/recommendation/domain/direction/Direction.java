package dev.project.recommendation.domain.direction;

import dev.project.recommendation.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "directions")
@Entity
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객 주소 정보
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 추천 편의점 정보
    private String targetStoreName;
    @Column(columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '-'")
    private String targetStoreUrl;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 고객 주소와 추천 편의점간의 거리
    private double distance;

    @Builder
    private Direction(String inputAddress,
                      double inputLatitude,
                      double inputLongitude,
                      String targetStoreName,
                      String targetStoreUrl,
                      String targetAddress,
                      double targetLatitude,
                      double targetLongitude,
                      double distance) {

        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetStoreName = targetStoreName;
        this.targetStoreUrl = (StringUtils.hasText(targetStoreUrl)) ? targetStoreUrl : "-";
        this.targetAddress = targetAddress;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.distance = distance;
    }
}
