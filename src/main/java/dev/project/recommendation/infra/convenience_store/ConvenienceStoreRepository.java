package dev.project.recommendation.infra.convenience_store;

import dev.project.recommendation.domain.convenience_store.ConvenienceStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvenienceStoreRepository extends JpaRepository<ConvenienceStore, Long> {
}
