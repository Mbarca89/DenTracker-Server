package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.PatientMapper;
import com.mbarca89.DenTracker.service.patient.PatientService;
import com.mbarca89.DenTracker.service.main.JwtService;
import com.mbarca89.DenTracker.util.AuditHelper;
import com.mbarca89.DenTracker.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DentalEvaluationRepository dentalEvaluationRepository;
    private final DentalPredictionRepository dentalPredictionRepository;
    private final GalleryRepository galleryRepository;
    private final HealthQuestionnaireRepository healthQuestionnaireRepository;
    private final LabsRepository labsRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;
    private final PatientMapper patientMapper;
    private final SessionUtils sessionUtils;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto dto, HttpServletRequest request) {
        Patient patient = patientMapper.toEntity(dto);
        AuditHelper.setAuditFields(patient, request, jwtService);

        Patient saved = patientRepository.save(patient);

        dentalEvaluationRepository.save(new DentalEvaluation(saved));
        dentalPredictionRepository.save(new DentalPrediction(saved));
        galleryRepository.save(new Gallery(saved));
        healthQuestionnaireRepository.save(new HealthQuestionnaire(saved));
        labsRepository.save(new Labs(saved));
        medicalHistoryRepository.save(new MedicalHistory(saved));

        return patientMapper.toDto(saved);
    }

    @Override
    public List<PatientResponseDto> getAllPatients(HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        List<Patient> patients = patientRepository.findByClientIdAndActiveTrue(clientId);
        return patientMapper.toDtoList(patients);
    }

    @Override
    public PatientResponseDto getPatientById(Long id, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        Patient patient = patientRepository.findByIdAndClientIdAndActiveTrue(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));
        return patientMapper.toDto(patient);
    }

    @Override
    public List<PatientResponseDto> searchByName(String name, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        return patientRepository.searchByNameAndClientId(name, clientId).stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    public void updatePatient(Long id, PatientRequestDto dto, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);

        Patient patient = patientRepository.findByIdAndClientIdAndActiveTrue(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));

        patientMapper.updateEntity(patient, dto);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);

        Patient patient = patientRepository.findByIdAndClientIdAndActiveTrue(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));

        // borrar archivos asociados
        fileStorageService.deletePatientDirectory(patient.getLastName() + "_" + patient.getFirstName());

        patient.setActive(false); // soft delete
        patientRepository.save(patient);
    }

    @Override
    public void hardDeletePatient(Long id, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);

        Patient patient = patientRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));

        fileStorageService.deletePatientDirectory(patient.getLastName() + "_" + patient.getFirstName());
        patientRepository.delete(patient);
    }

}
