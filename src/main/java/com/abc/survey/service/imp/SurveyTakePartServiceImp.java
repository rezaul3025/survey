package com.abc.survey.service.imp;

import com.abc.survey.exception.InvalidInputException;
import com.abc.survey.model.*;
import com.abc.survey.repository.PatientRepository;
import com.abc.survey.repository.SurveyRepository;
import com.abc.survey.repository.SurveyTakePartRepository;
import com.abc.survey.api.request.SurveyResultRequest;
import com.abc.survey.api.request.SurveyTakePartRequest;
import com.abc.survey.api.response.SurveyResultResponse;
import com.abc.survey.api.response.SurveyTakePartResponse;
import com.abc.survey.service.SurveyTakePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SurveyTakePartServiceImp implements SurveyTakePartService {

    private final SurveyTakePartRepository surveyTakePartRepository;

    private final PatientRepository patientRepository;

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyTakePartServiceImp(final SurveyTakePartRepository surveyTakePartRepository,
    final PatientRepository patientRepository,  final SurveyRepository surveyRepository){
        this.surveyTakePartRepository = surveyTakePartRepository;
        this.patientRepository = patientRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public SurveyTakePartResponse takePartInSurvey(SurveyTakePartRequest surveyTakePartRequest) {

        Patient patient = patientRepository.findById(surveyTakePartRequest.getPatientId()).orElseThrow(
                ()->new InvalidInputException("Patient not found")
        );

        Survey survey = surveyRepository.findById(surveyTakePartRequest.getSurveyId()).orElseThrow(
                ()->new InvalidInputException("Survey not found")
        );

        SurveyTakePart surveyTakePart = SurveyTakePart.builder()
                        .surveyResults(toSurveyResult(surveyTakePartRequest.getResults()))
                        .patient(patient)
                        .survey(survey)
                        .time(new Date())
                        .build();

        surveyTakePart = surveyTakePartRepository.save(surveyTakePart);

      return SurveyTakePartResponse.builder()
                .id(surveyTakePart.getId())
                .surveyId(surveyTakePart.getSurvey().getId())
                .patientId(surveyTakePart.getPatient().getId())
                .results(toSurveyResultResponse(surveyTakePart.getSurveyResults()))
                .build();

    }

    private List<SurveyResult> toSurveyResult(Set<SurveyResultRequest> surveyResultRequests){
        return surveyResultRequests.stream()
                .map(s -> SurveyResult.builder().question(s.getQuestion()).answer(s.getAnswer()).build())
                .collect(Collectors.toList());
    }

    private Set<SurveyResultResponse> toSurveyResultResponse(List<SurveyResult> surveyResults){
        return  surveyResults.stream()
                .map(s ->SurveyResultResponse.builder().question(s.getQuestion()).answer(s.getAnswer()).build())
                .collect(Collectors.toSet());
    }
}
