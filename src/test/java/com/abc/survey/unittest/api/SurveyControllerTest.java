package com.abc.survey.unittest.api;

import com.abc.survey.api.controller.SurveyController;
import com.abc.survey.api.request.SurveyResultRequest;
import com.abc.survey.api.request.SurveyTakePartRequest;
import com.abc.survey.api.response.SurveyResultResponse;
import com.abc.survey.api.response.SurveyTakePartResponse;
import com.abc.survey.service.SurveyService;
import com.abc.survey.service.SurveyTakePartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SurveyController.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyTakePartService surveyTakePartService;

    @MockBean
    private SurveyService surveyService;

    private SurveyTakePartRequest request;

    private SurveyTakePartResponse response;

    @BeforeEach
    public void setUp(){
        request = SurveyTakePartRequest.builder()
                .patientId(2L)
                .surveyId(3L)
                .results(generateSurveyResultRequest())
                .build();

        response = SurveyTakePartResponse.builder()
                .id(4L)
                .patientId(2L)
                .surveyId(3L)
                .results(generateSurveyResultResponses())
                .build();
    }

    @Test
    public void patientShouldBeAbleToTakePartInSurvey() throws Exception {
        given(surveyTakePartService.takePartInSurvey(request)).willReturn(response);
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"surveyId\":1,\"patientId\":1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isCreated());
    }

    private Set<SurveyResultRequest> generateSurveyResultRequest(){
        Set<SurveyResultRequest> results = new HashSet<>();

        SurveyResultRequest request = SurveyResultRequest.builder()
                .question("test")
                .answer(6)
                .build();
        results.add(request);

        SurveyResultRequest request1 = SurveyResultRequest.builder()
                .question("test1")
                .answer(7)
                .build();
        results.add(request1);

        return results;
    }

    private Set<SurveyResultResponse> generateSurveyResultResponses(){

        Set<SurveyResultResponse> resultResponseSet = new HashSet<>();

        SurveyResultResponse response1 = SurveyResultResponse.builder()
                .question("test")
                .answer(6)
                .build();
        resultResponseSet.add(response1);

        SurveyResultResponse response2 = SurveyResultResponse.builder()
                .question("test1")
                .answer(7)
                .build();
        resultResponseSet.add(response2);

        return resultResponseSet;
    }
}
