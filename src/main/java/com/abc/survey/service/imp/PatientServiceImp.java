package com.abc.survey.service.imp;

import com.abc.survey.model.Patient;
import com.abc.survey.repository.PatientRepository;
import com.abc.survey.api.request.PatientRequest;
import com.abc.survey.api.response.PatientResponse;
import com.abc.survey.service.PatientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PatientServiceImp  implements PatientService {

    private final PatientRepository repository;

    PatientServiceImp(final PatientRepository repository){
        this.repository = repository;
    }

    @Override
    public PatientResponse create(PatientRequest request) {

        Patient patient = Patient.builder()
                .name(request.getName())
                .build();

        repository.save(patient);

        return  PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .build();
    }
}
