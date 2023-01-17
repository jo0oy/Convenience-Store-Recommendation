package dev.project.recommendation.domain.convenience_store;

import java.util.List;
import java.util.Optional;

public interface ConvenienceStoreCache {

    void save(ConvenienceStoreCacheDto storeCacheDto);

    void saveAll(List<ConvenienceStoreCacheDto> storeCacheDtoList);

    Optional<ConvenienceStoreCacheDto> findById(Long id);

    List<ConvenienceStoreCacheDto> findAll();

    void delete(Long id);

    void deleteAll();
}
