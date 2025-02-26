package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.entity.client.Patient;
import com.mbarca89.DenTracker.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }
}