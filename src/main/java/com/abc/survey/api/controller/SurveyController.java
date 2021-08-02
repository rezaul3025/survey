package com.abc.survey.api.controller;

import com.abc.survey.api.request.SurveyRequest;
import com.abc.survey.api.request.SurveyTakePartRequest;
import com.abc.survey.api.response.SurveyResponse;
import com.abc.survey.api.response.SurveyTakePartResponse;
import com.abc.survey.service.SurveyService;
import com.abc.survey.service.SurveyTakePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/survey")
public class SurveyController {

    private final SurveyService surveyService;

    private final SurveyTakePartService surveyTakePartService;

    @Autowired
    SurveyController(final SurveyService surveyService, final SurveyTakePartService
                     surveyTakePartService) {
        this.surveyService = surveyService;
        this.surveyTakePartService = surveyTakePartService;
    }

    @PostMapping
    public SurveyResponse create(@RequestBody SurveyRequest request){
       return this.surveyService.create(request);
    }

    @PostMapping(value = "/take-part")
    public ResponseEntity<SurveyTakePartResponse> takePart(@RequestBody @Valid SurveyTakePartRequest surveyTakePartRequest){
        return  new ResponseEntity(surveyTakePartService.takePartInSurvey(surveyTakePartRequest), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
