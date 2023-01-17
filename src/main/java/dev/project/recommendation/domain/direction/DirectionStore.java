package dev.project.recommendation.domain.direction;

import java.util.List;

public interface DirectionStore {

    Direction store(Direction direction);

    List<Direction> storeAll(List<Direction> directionList);
}
