package dev.project.recommendation.domain.convenience_store;

import dev.project.recommendation.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "convenience_stores")
@Entity
public class ConvenienceStore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;

    @Builder
    private ConvenienceStore(String storeName,
                             String storeAddress,
                             double latitude,
                             double longitude) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void changeStoreAddress(String address) {
        if (StringUtils.hasText(address)) {
            this.storeAddress = address;
        }
    }
}
