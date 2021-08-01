package com.abc.survey.api.controller;

import com.abc.survey.api.request.PatientRequest;
import com.abc.survey.api.response.PatientResponse;
import com.abc.survey.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    PatientController(final PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping
    public PatientResponse create(@RequestBody PatientRequest request){
        return  patientService.create(request);
    }
}
