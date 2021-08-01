package com.abc.survey.service;

import com.abc.survey.api.request.SurveyTakePartRequest;
import com.abc.survey.api.response.SurveyTakePartResponse;

public interface SurveyTakePartService {
    public SurveyTakePartResponse takePartInSurvey(SurveyTakePartRequest surveyTakePartRequest);
}
