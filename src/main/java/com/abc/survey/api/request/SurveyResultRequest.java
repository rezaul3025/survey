package com.abc.survey.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyResultRequest {

    private String question;

    private Integer answer;
}
