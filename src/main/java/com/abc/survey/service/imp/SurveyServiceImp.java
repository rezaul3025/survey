package com.abc.survey.service.imp;

import com.abc.survey.api.request.PatientRequest;
import com.abc.survey.api.request.SurveyRequest;
import com.abc.survey.api.response.SurveyResponse;
import com.abc.survey.model.Survey;
import com.abc.survey.repository.SurveyRepository;
import com.abc.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class SurveyServiceImp implements SurveyService {

    private final SurveyRepository repository;

    @Autowired
    SurveyServiceImp(final SurveyRepository repository){
        this.repository = repository;
    }

    @Override
    public SurveyResponse create(SurveyRequest surveyRequest) {
        Survey survey = Survey.builder()
                .description(surveyRequest.getDescription())
                .build();
        repository.save(survey);

        return SurveyResponse
                .builder()
                .id(survey.getId())
                .description(survey.getDescription())
                .build();
    }
}
