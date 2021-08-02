package com.abc.survey.unittest.service;

import com.abc.survey.api.request.SurveyResultRequest;
import com.abc.survey.api.request.SurveyTakePartRequest;
import com.abc.survey.api.response.SurveyTakePartResponse;
import com.abc.survey.exception.InvalidInputException;
import com.abc.survey.model.Patient;
import com.abc.survey.model.Survey;
import com.abc.survey.model.SurveyResult;
import com.abc.survey.model.SurveyTakePart;
import com.abc.survey.repository.PatientRepository;
import com.abc.survey.repository.SurveyRepository;
import com.abc.survey.repository.SurveyTakePartRepository;


import com.abc.survey.service.imp.SurveyTakePartServiceImp;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurveyTakePartServiceTest {

    @Mock
    private SurveyTakePartRepository surveyTakePartRepo;

    @Mock
    private PatientRepository patientRepo;

    @Mock
    private SurveyRepository surveyRepo;

    @InjectMocks
    private SurveyTakePartServiceImp surveyTakePartService;

    private SurveyTakePartRequest request;

    @Test
    public void patientShouldBeAbleToTakePartInSurvey() throws Exception {
        // given
        Patient patient = Patient.builder().id(2L).name("test").build();
        Survey survey = Survey.builder().id(3L).description("test").build();
        SurveyTakePart surveyTakePart = SurveyTakePart.builder()
                .patient(patient)
                .survey(survey)
                .surveyResults(toSurveyResult(generateSurveyResultRequest()))
                .time(new Date())
                .build();
        when(patientRepo.findById(2L)).thenReturn(Optional.of(patient));
        when(surveyRepo.findById(3L)).thenReturn(Optional.of(survey));
        when(surveyTakePartRepo.save(any(SurveyTakePart.class))).thenReturn(surveyTakePart);

        //when
        request = SurveyTakePartRequest.builder()
                .patientId(2L)
                .surveyId(3L)
                .results(generateSurveyResultRequest())
                .build();
        SurveyTakePartResponse surveyTakePartRes = surveyTakePartService.takePartInSurvey(request);

        //Then
        assertThat(surveyTakePartRes).isNotNull();
        assertThat(surveyTakePartRes.getSurveyId()).isEqualTo(3L);
        assertThat(surveyTakePartRes.getPatientId()).isEqualTo(2L);
        assertThat(surveyTakePartRes.getResults()).hasSize(2);
        assertThat(surveyTakePartRes.getResults().stream().anyMatch(e->e.getQuestion().equals("test") && e.getAnswer()==6)).isTrue();
        assertThat(surveyTakePartRes.getResults().stream().anyMatch(e->e.getQuestion().equals("test1") && e.getAnswer()==7)).isTrue();
    }

    @Test
    public void surveyCannotTakePlaceWithoutPatient()throws Exception {
        //when
        request = SurveyTakePartRequest.builder()
                .patientId(2L)
                .surveyId(3L)
                .results(generateSurveyResultRequest())
                .build();
        Exception exception = assertThrows(
                InvalidInputException.class,
                () ->   surveyTakePartService.takePartInSurvey(request));

        //then
        assertThat(exception.getMessage()).isEqualTo("Patient not found");
    }

    @Test
    public void surveyCannotTakePlaceBeforeSurveyConfigured()throws Exception {
        // given
        Patient patient = Patient.builder().id(2L).name("test").build();

        when(patientRepo.findById(2L)).thenReturn(Optional.of(patient));
        //when
        request = SurveyTakePartRequest.builder()
                .patientId(2L)
                .surveyId(3L)
                .results(generateSurveyResultRequest())
                .build();
        Exception exception = assertThrows(
                InvalidInputException.class,
                () ->   surveyTakePartService.takePartInSurvey(request));

        //then
        assertThat(exception.getMessage()).isEqualTo("Survey not found");
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

    private List<SurveyResult> toSurveyResult(Set<SurveyResultRequest> surveyResultRequests){
        return surveyResultRequests.stream()
                .map(s -> SurveyResult.builder().question(s.getQuestion()).answer(s.getAnswer()).build())
                .collect(Collectors.toList());
    }
}
