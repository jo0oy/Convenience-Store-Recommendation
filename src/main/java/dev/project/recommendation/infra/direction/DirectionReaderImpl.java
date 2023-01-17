package dev.project.recommendation.infra.direction;

import dev.project.recommendation.domain.direction.Direction;
import dev.project.recommendation.domain.direction.DirectionReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DirectionReaderImpl implements DirectionReader {

    private final DirectionRepository directionRepository;

    @Override
    public Direction findDirection(Long directionId) {
        log.info("[DirectionReaderImpl getDirection] id: {}", directionId);
        return directionRepository.findById(directionId).orElse(null);
    }

    @Override
    public List<Direction> findAll() {
        log.info("[DirectionReaderImpl getDirectionList]");

        return directionRepository.findAll();
    }
}
