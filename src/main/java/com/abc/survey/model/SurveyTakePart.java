package com.abc.survey.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "survey_take_part")
public class SurveyTakePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Survey survey;

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "surveyTakePart", cascade=CascadeType.ALL)
    private List<SurveyResult> surveyResults;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
