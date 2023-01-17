package dev.project.recommendation.infra.convenience_store;

import dev.project.recommendation.domain.convenience_store.ConvenienceStore;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConvenienceStoreStoreImpl implements ConvenienceStoreStore {

    private final ConvenienceStoreRepository convenienceStoreRepository;

    @Override
    public ConvenienceStore store(ConvenienceStore store) {
        return convenienceStoreRepository.save(store);
    }
}
