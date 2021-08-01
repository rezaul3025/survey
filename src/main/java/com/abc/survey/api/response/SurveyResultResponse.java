package com.abc.survey.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyResultResponse {
    private String question;

    private Integer answer;
}
