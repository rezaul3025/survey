package com.abc.survey.api.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class SurveyTakePartRequest {

    @NotNull(message = "Survey id cannot null")
    private Long surveyId;

    @NotNull(message = "Patient id cannot null")
    private Long patientId;

    Set<SurveyResultRequest> results;

}
