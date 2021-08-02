package com.abc.survey.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@Table(name = "patient")
@RequiredArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="patient", cascade=CascadeType.ALL)
    private Set<SurveyTakePart> surveyTakeParts;
}
