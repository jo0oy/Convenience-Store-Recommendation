package dev.project.recommendation.domain.direction;

import java.util.List;

public interface DirectionReader {

    Direction findDirection(Long directionId);

    List<Direction> findAll();
}
