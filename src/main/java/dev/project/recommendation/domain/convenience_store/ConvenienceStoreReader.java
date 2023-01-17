package dev.project.recommendation.domain.convenience_store;

import java.util.List;

public interface ConvenienceStoreReader {

    ConvenienceStore findConvenienceStore(Long storeId);

    List<ConvenienceStore> findAll();
}
