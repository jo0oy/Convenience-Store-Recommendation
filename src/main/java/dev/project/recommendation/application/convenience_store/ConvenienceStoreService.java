package dev.project.recommendation.application.convenience_store;

import dev.project.recommendation.application.dto.ConvenienceStoreDto;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCache;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCacheDto;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConvenienceStoreService {

    private final ConvenienceStoreReader convenienceStoreReader;
    private final ConvenienceStoreCache convenienceStoreCache;

    public List<ConvenienceStoreDto> searchConvenienceStoreDtoList() {

        // redis
        var cacheResultList = convenienceStoreCache.findAll();
        if (!CollectionUtils.isEmpty(cacheResultList)) {
            log.info("ConvenienceStore Redis Result is not empty");
            return cacheResultList.stream()
                    .map(ConvenienceStoreDto::of).collect(Collectors.toList());
        }

        // db
        var dbResultList =  convenienceStoreReader.findAll();

        convenienceStoreCache.saveAll(
                dbResultList.stream()
                        .map(ConvenienceStoreCacheDto::fromEntity)
                        .collect(Collectors.toList())); // db 에서 조회한 편의점 리스트 cache(redis)에 저장.

        return dbResultList.stream()
                .map(ConvenienceStoreDto::fromEntity)
                .collect(Collectors.toList());
    }
}
