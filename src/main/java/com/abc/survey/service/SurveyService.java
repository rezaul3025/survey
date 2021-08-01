package com.abc.survey.service;

import com.abc.survey.api.request.SurveyRequest;
import com.abc.survey.api.response.SurveyResponse;

public interface SurveyService {
    SurveyResponse create(SurveyRequest request);
}
