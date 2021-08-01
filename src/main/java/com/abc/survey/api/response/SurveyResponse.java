package com.abc.survey.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyResponse {
    private Long id;
    private String description;

}
