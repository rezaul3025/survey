package com.abc.survey.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SurveyTakePartResponse {
    private Long id;
    private Long surveyId;

    private Long patientId;

    Set<SurveyResultResponse> results;
}
