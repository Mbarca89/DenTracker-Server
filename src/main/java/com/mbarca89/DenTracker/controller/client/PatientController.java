package com.mbarca89.DenTracker.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbarca89.DenTracker.dto.request.PatientRequestDto;
import com.mbarca89.DenTracker.entity.client.Patient;
import com.mbarca89.DenTracker.mapper.PatientMapper;
import com.mbarca89.DenTracker.service.client.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPatient(@RequestParam(value = "file", required = false) MultipartFile file,
                                           @Valid @RequestBody PatientRequestDto patientJson) throws Exception {
        Patient patient = PatientMapper.INSTANCE.toPatient(patientJson);

        patientService.createPatient(patient);

        return ResponseEntity.status(HttpStatus.OK).body("Paciente creado correctamente");
    }
}
