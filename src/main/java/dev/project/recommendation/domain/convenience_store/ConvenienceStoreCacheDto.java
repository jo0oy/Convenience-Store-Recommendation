package dev.project.recommendation.domain.convenience_store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConvenienceStoreCacheDto implements Serializable {

    private Long id;
    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;
    private LocalDateTime createdDate;

    public static ConvenienceStoreCacheDto fromEntity(ConvenienceStore entity) {
        return ConvenienceStoreCacheDto.builder()
                .id(entity.getId())
                .storeName(entity.getStoreName())
                .storeAddress(entity.getStoreAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
