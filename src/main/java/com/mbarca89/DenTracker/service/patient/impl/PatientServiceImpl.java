package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.patient.*;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.PatientMapper;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import com.mbarca89.DenTracker.repository.patient.*;
import com.mbarca89.DenTracker.service.file.FileStorageService;
import com.mbarca89.DenTracker.service.patient.PatientService;
import com.mbarca89.DenTracker.service.main.JwtService;
import com.mbarca89.DenTracker.utils.AuditHelper;
import com.mbarca89.DenTracker.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ClientUserRepository clientUserRepository;
    private final DentalEvaluationRepository dentalEvaluationRepository;
    private final DentalPredictionRepository dentalPredictionRepository;
    private final GalleryRepository galleryRepository;
    private final HealthQuestionnaireRepository healthQuestionnaireRepository;
    private final LabsRepository labsRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;
    private final PatientMapper patientMapper;

    @Transactional
    @Override
    public PatientResponseDto createPatient(PatientRequestDto dto, HttpServletRequest request) {
        Patient patient = patientMapper.toEntity(dto);
        AuditHelper.setAuditFields(patient, request, jwtService);

        Patient saved = patientRepository.save(patient);

        // Dental Evaluation
        DentalEvaluation dentalEvaluation = new DentalEvaluation(saved);
        AuditHelper.setAuditFields(dentalEvaluation, request, jwtService);
        dentalEvaluationRepository.save(dentalEvaluation);

        // Dental Prediction
        DentalPrediction dentalPrediction = new DentalPrediction(saved);
        AuditHelper.setAuditFields(dentalPrediction, request, jwtService);
        dentalPredictionRepository.save(dentalPrediction);

        // Gallery
        Gallery gallery = new Gallery(saved);
        AuditHelper.setAuditFields(gallery, request, jwtService);
        galleryRepository.save(gallery);

        // Health Questionnaire
        HealthQuestionnaire healthQuestionnaire = new HealthQuestionnaire(saved);
        AuditHelper.setAuditFields(healthQuestionnaire, request, jwtService);
        healthQuestionnaireRepository.save(healthQuestionnaire);

        // Labs
        Labs labs = new Labs(saved);
        AuditHelper.setAuditFields(labs, request, jwtService);
        labsRepository.save(labs);

        // Medical History
        MedicalHistory medicalHistory = new MedicalHistory(saved);
        AuditHelper.setAuditFields(medicalHistory, request, jwtService);
        medicalHistoryRepository.save(medicalHistory);

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

        fileStorageService.deleteAllPatientFiles(clientId, patient.getId());
        patient.setActive(false);
        patientRepository.save(patient);
    }

    @Override
    public void hardDeletePatient(Long id, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);

        Patient patient = patientRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));

        fileStorageService.deleteAllPatientFiles(clientId, patient.getId());
        patientRepository.delete(patient);
    }

    @Override
    public void transferPatient(Long patientId, Long targetUserId, HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        Long currentUserId = SessionUtils.getAuthenticatedClientUserId();

        // Validar que el paciente exista y sea del cliente
        Patient patient = patientRepository.findByIdAndClientIdAndActiveTrue(patientId, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado."));

        // Validar que el subusuario actual sea quien lo creó
        if (!patient.getCreatedByUserId().equals(currentUserId)) {
            throw new IllegalStateException("Solo el creador del paciente puede trasladarlo.");
        }

        // Validar que el usuario destino pertenezca al mismo cliente
        ClientUser targetUser = clientUserRepository.findById(targetUserId)
                .filter(user -> user.getClient().getId().equals(clientId))
                .orElseThrow(() -> new ResourceNotFoundException("Subusuario destino no válido."));

        patient.setCreatedByUserId(targetUser.getId());
        patientRepository.save(patient);
    }

    @Override
    public List<PatientResponseDto> getPatientsByUser(HttpServletRequest request) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        Long userId = SessionUtils.getAuthenticatedClientUserId();

        List<Patient> patients = patientRepository
                .findByClientIdAndCreatedByUserIdAndActiveTrue(clientId, userId);

        return patientMapper.toDtoList(patients);
    }

}
