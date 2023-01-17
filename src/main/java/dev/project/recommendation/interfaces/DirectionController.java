package dev.project.recommendation.interfaces;

import dev.project.recommendation.application.direction.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String getOriginDirectionUrl(@PathVariable("encodedId") String encodedId) {
        String url = directionService.findDirectionUrlByEncodedId(encodedId);

        log.info("[DirectionController searchDirection] direction url: {}", url);

        return "redirect:" + url;
    }
}
