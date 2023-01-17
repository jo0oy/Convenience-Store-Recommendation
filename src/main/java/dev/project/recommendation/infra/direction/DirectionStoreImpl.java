package dev.project.recommendation.infra.direction;

import dev.project.recommendation.domain.direction.Direction;
import dev.project.recommendation.domain.direction.DirectionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DirectionStoreImpl implements DirectionStore {

    private final DirectionRepository directionRepository;

    @Override
    public Direction store(Direction direction) {
        return directionRepository.save(direction);
    }

    @Override
    public List<Direction> storeAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }
}
