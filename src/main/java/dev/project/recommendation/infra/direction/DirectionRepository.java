package dev.project.recommendation.infra.direction;

import dev.project.recommendation.domain.direction.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
