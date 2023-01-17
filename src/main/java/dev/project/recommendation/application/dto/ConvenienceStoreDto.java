package dev.project.recommendation.application.dto;

import dev.project.recommendation.domain.convenience_store.ConvenienceStore;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCacheDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvenienceStoreDto {

    private Long id;
    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;

    public static ConvenienceStoreDto fromEntity(ConvenienceStore entity) {
        return ConvenienceStoreDto.builder()
                .id(entity.getId())
                .storeName(entity.getStoreName())
                .storeAddress(entity.getStoreAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }

    public static ConvenienceStoreDto of(ConvenienceStoreCacheDto cacheDto) {
        return ConvenienceStoreDto.builder()
                .id(cacheDto.getId())
                .storeName(cacheDto.getStoreName())
                .storeAddress(cacheDto.getStoreAddress())
                .latitude(cacheDto.getLatitude())
                .longitude(cacheDto.getLongitude())
                .build();
    }
}
