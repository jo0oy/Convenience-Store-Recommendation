package dev.project.recommendation.infra.convenience_store;

import dev.project.recommendation.domain.convenience_store.ConvenienceStore;
import dev.project.recommendation.domain.convenience_store.ConvenienceStoreReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConvenienceStoreReaderImpl implements ConvenienceStoreReader {

    private final ConvenienceStoreRepository convenienceStoreRepository;

    @Override
    public ConvenienceStore findConvenienceStore(Long storeId) {
        return convenienceStoreRepository.findById(storeId).orElse(null);
    }

    @Override
    public List<ConvenienceStore> findAll() {
        return convenienceStoreRepository.findAll();
    }
}
