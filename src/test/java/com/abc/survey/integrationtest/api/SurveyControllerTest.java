package com.abc.survey.integrationtest.api;

import com.abc.survey.api.controller.SurveyController;
import com.abc.survey.api.request.PatientRequest;
import com.abc.survey.api.request.SurveyRequest;
import com.abc.survey.config.ContainersEnvironment;
import com.abc.survey.service.PatientService;
import com.abc.survey.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class SurveyControllerTest extends ContainersEnvironment {

    @Autowired
    private SurveyController surveyController;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        setUpInitData();
    }

    @Test
    public void contextLoadsTest(){
        assertThat(surveyController).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(patientService).isNotNull();
        assertThat(surveyService).isNotNull();
    }

    @Test
    public void patientShouldBeAbleToTakePartInSurveySuccessfully() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"surveyId\":1,\"patientId\":1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.surveyId", is(1)))
                .andExpect(jsonPath("$.patientId", is(1)))
                .andExpect(jsonPath("$.results[0].question", is("test")))
                .andExpect(jsonPath("$.results[0].answer", is(2)));

    }

    @Test
    public void surveyCannotTakePlaceWithoutPatient()throws Exception {
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"surveyId\":1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"patientId\":\"Patient id cannot null\"}"));
    }

    @Test
    public void surveyCannotTakePlaceWhenPatientNotFound()throws Exception {
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"surveyId\":1,\"patientId\":-1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("Patient not found"));
    }

    @Test
    public void surveyCannotTakePlaceBeforeSurveyConfigured()throws Exception {
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"patientId\":1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"surveyId\":\"Survey id cannot null\"}"));
    }

    @Test
    public void surveyCannotTakePlaceWithoutSurveyInformation()throws Exception {
        this.mockMvc
                .perform(
                        post("/api/v1/survey/take-part")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"surveyId\":-1,\"patientId\":1, \"results\":[{\"question\":\"test\", \"answer\":\"2\"}]}")
                ).andExpect(status().isBadRequest())
                .andExpect(content().string("Survey not found"));
    }

    private void setUpInitData(){
        patientService.create(PatientRequest.builder().name("test patient").build());
        surveyService.create(SurveyRequest.builder().description("Test").build());
    }
}
