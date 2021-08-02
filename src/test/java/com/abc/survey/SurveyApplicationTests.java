package com.abc.survey;

import com.abc.survey.api.controller.PatientController;
import com.abc.survey.api.controller.SurveyController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SurveyApplicationTests {

	@Autowired
	private SurveyController surveyController;

	@Autowired
	private PatientController patientController;

	@Test
	void contextLoads() {
		assertThat(surveyController).isNotNull();
		assertThat(patientController).isNotNull();
	}

}
