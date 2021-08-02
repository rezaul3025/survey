package com.abc.survey.repository;

import com.abc.survey.model.SurveyTakePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyTakePartRepository extends JpaRepository<SurveyTakePart, Long> {

}
