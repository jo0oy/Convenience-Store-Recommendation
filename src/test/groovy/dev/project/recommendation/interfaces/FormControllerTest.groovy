package dev.project.recommendation.interfaces

import dev.project.recommendation.application.convenience_store.ConvenienceStoreRecommendationService
import dev.project.recommendation.application.dto.OutputDto
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private ConvenienceStoreRecommendationService convenienceStoreRecommendationService = Mock()

    private List<OutputDto> outputDtoList

    def setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new FormController(convenienceStoreRecommendationService)).build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                        .storeName("편의점1").build(),
                OutputDto.builder()
                        .storeName("편의점2").build()
        )
    }

    def "GET /" () {
        expect:
        mockMvc.perform(get("/"))
                .andExpect(handler().handlerType(FormController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andDo(log())
    }

    def "POST /search" () {
        given:
        String inputAddress = "서울특별시 송파구 올림픽로 240"

        when:
        def resultActions = mockMvc.perform(post("/search")
                .param("address", inputAddress))

        then:
        1 * convenienceStoreRecommendationService
                .recommendConvenienceStoreList(arg -> {
            assert arg == inputAddress}) >> outputDtoList

        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputList"))
                .andDo(print())

    }
}
