package com.abc.survey.integrationtest.repository;

import com.abc.survey.config.ContainersEnvironment;
import com.abc.survey.model.Patient;
import com.abc.survey.model.Survey;
import com.abc.survey.model.SurveyResult;
import com.abc.survey.model.SurveyTakePart;
import com.abc.survey.repository.PatientRepository;
import com.abc.survey.repository.SurveyRepository;
import com.abc.survey.repository.SurveyTakePartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class SurveyTakePartRepositoryIntegrationTest extends ContainersEnvironment {

    @Autowired
    private SurveyTakePartRepository underTest;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @BeforeEach
    public void setUp(){
        Patient patient = Patient.builder()
        .name("test")
        .build();
        patientRepository.save(patient);
        Survey survey =Survey.builder()
                .description("Test Survey")
                .build();
        surveyRepository.save(survey);
    }

    @Test
    public void shouldSurveyTakePart() {
        // given
        SurveyTakePart surveyTakePart = SurveyTakePart.builder()
                .patient(patientRepository.getById(1L))
                .survey(surveyRepository.getById(1L))
                .time(new Date())
                .surveyResults(generateSurveyResult())
                .build();

        //when
        underTest.save(surveyTakePart);

        //Then
        Optional<SurveyTakePart> resultOptional = underTest.findById(surveyTakePart.getId());
        assertThat(resultOptional).isNotNull();
        SurveyTakePart result = resultOptional.get();
        assertThat(result.getId()).isEqualTo(1L);

        assertThat(result.getSurvey()).isNotNull();
        assertThat(result.getSurvey().getDescription()).isEqualTo("Test Survey");

        assertThat(result.getPatient()).isNotNull();
        assertThat(result.getPatient().getName()).isEqualTo("test");

        assertThat(result.getSurveyResults()).hasSize(2);
        SurveyResult surveyResult1 = result.getSurveyResults().get(0);
        assertThat(surveyResult1).isNotNull();
        assertThat(surveyResult1.getQuestion()).isEqualTo("How was your sleep last night?");
        assertThat(surveyResult1.getAnswer()).isEqualTo(6);

        SurveyResult surveyResult2 = result.getSurveyResults().get(1);
        assertThat(surveyResult2).isNotNull();
        assertThat(surveyResult2.getQuestion()).isEqualTo("How good is your skin condition?");
        assertThat(surveyResult2.getAnswer()).isEqualTo(8);

    }

    private List<SurveyResult> generateSurveyResult(){
        SurveyResult result1 = SurveyResult.builder()
                .question("How was your sleep last night?")
                .answer(6)
                .build();
        SurveyResult result2 = SurveyResult.builder()
                .question("How good is your skin condition?")
                .answer(8)
                .build();

        List<SurveyResult> results = new ArrayList<>();
        results.add(result1);
        results.add(result2);

        return results;
    }
}
