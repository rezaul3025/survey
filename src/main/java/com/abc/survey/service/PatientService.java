package com.abc.survey.service;

import com.abc.survey.api.request.PatientRequest;
import com.abc.survey.api.response.PatientResponse;

public interface PatientService {
    PatientResponse create(PatientRequest request);
}
