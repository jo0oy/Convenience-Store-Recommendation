package dev.project.recommendation.infra.convenience_store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCache;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreCacheDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConvenienceStoreCacheImpl implements ConvenienceStoreCache {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final static String CACHE_KEY = "CONVENIENCE_STORE";
    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(ConvenienceStoreCacheDto storeCacheDto) {
        if(Objects.isNull(storeCacheDto) || Objects.isNull(storeCacheDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY, String.valueOf(storeCacheDto.getId()), serialize(storeCacheDto)); // redis 에 편의점 엔티티 저장
            log.info("[ConvenienceStoreCacheImpl save success] id: {}", storeCacheDto.getId());
        } catch (Exception e) {
            log.error("[ConvenienceStoreCacheImpl save error] {}", e.getMessage());
        }
    }

    @Override
    public void saveAll(List<ConvenienceStoreCacheDto> storeCacheDtoList) {
        if(CollectionUtils.isEmpty(storeCacheDtoList)) return;

        for (ConvenienceStoreCacheDto storeCacheDto : storeCacheDtoList) {
            save(storeCacheDto);
        }
    }

    @Override
    public Optional<ConvenienceStoreCacheDto> findById(Long id) {
        try {
            var convenienceStore = hashOperations.get(CACHE_KEY, String.valueOf(id));// redis 에서 편의점 엔티티 get
            log.info("[ConvenienceStoreCacheImpl findById success] id: {}", id);
            return Optional.ofNullable(deserialize(convenienceStore));

        } catch (Exception e) {
            log.error("[ConvenienceStoreCacheImpl findById error] {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<ConvenienceStoreCacheDto> findAll() {
        try {
            var list =  new ArrayList<ConvenienceStoreCacheDto>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                list.add(deserialize(value));
            }
            log.info("[ConvenienceStoreCacheImpl findAll success]");

            return list;

        } catch (Exception e) {
            log.error("[ConvenienceStoreCacheImpl findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            hashOperations.delete(CACHE_KEY, String.valueOf(id)); // cache 에서 삭제
            log.info("[ConvenienceStoreCacheImpl delete success] id: {}", id);

        } catch (Exception e) {
            log.error("[ConvenienceStoreCacheImpl delete error] {}", e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            for (String hashKey : findAllHashKeys()) {
                hashOperations.delete(CACHE_KEY, hashKey);
            }

            log.info("[ConvenienceStoreCacheImpl deleteAll success]");

        } catch (Exception e) {
            log.error("[ConvenienceStoreCacheImpl deleteAll error] {}", e.getMessage());
        }
    }

    private Set<String> findAllHashKeys() { // CACHE_KEY 아래에 있는 모든 Hash Key 조회
        return hashOperations.keys(CACHE_KEY);
    }

    private String serialize(ConvenienceStoreCacheDto storeCacheDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(storeCacheDto);
    }

    private ConvenienceStoreCacheDto deserialize(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, ConvenienceStoreCacheDto.class);
    }
}
