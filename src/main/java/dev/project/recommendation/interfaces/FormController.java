package dev.project.recommendation.interfaces;

import dev.project.recommendation.application.convenience_store.ConvenienceStoreRecommendationService;
import dev.project.recommendation.interfaces.dto.RequestDto;
import dev.project.recommendation.interfaces.dto.OutputResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class FormController {

    private final ConvenienceStoreRecommendationService recommendationService;

    @GetMapping({"", "/"})
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView searchConvenienceStore(@ModelAttribute RequestDto requestDto) {

        // 편의점 추천 리스트 조회
        var outputList = recommendationService.recommendConvenienceStoreList(requestDto.getAddress()).stream()
                .map(OutputResponseDto::of).collect(Collectors.toList());

        var modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputList", outputList);

        return modelAndView;
    }
}
