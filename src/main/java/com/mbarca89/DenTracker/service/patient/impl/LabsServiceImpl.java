package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.LabsObservationsRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.LabsResponseDto;
import com.mbarca89.DenTracker.entity.patient.Labs;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.LabsMapper;
import com.mbarca89.DenTracker.repository.patient.LabsRepository;
import com.mbarca89.DenTracker.repository.patient.PatientRepository;
import com.mbarca89.DenTracker.service.file.FileStorageService;
import com.mbarca89.DenTracker.service.patient.LabsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabsServiceImpl implements LabsService {

    private final LabsRepository labsRepository;
    private final PatientRepository patientRepository;
    private final FileStorageService fileStorageService;
    private final LabsMapper labsMapper;

    @Transactional
    @Override
    public void uploadLabFiles(Long patientId,  String labType, MultipartFile[] files) {
        Labs labs = labsRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el laboratorio para el paciente."));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("El paciente no existe."));

        String patientFolder = fileStorageService.getPatientFolder(patient.getClientId(), patientId, "labs/" + labType);
        List<String> filePaths = fileStorageService.saveFiles(files, patientFolder);

        // Agregar las rutas de archivos según el tipo de estudio
        switch (labType) {
            case "Hemograma":
                labs.getHemogram().addAll(filePaths);
                break;
            case "Glucemia":
                labs.getGlycemia().addAll(filePaths);
                break;
            case "Hemoglobina":
                labs.getHemoglobin().addAll(filePaths);
                break;
            case "Uremia":
                labs.getUraemia().addAll(filePaths);
                break;
            case "Coagulograma":
                labs.getCoagulagram().addAll(filePaths);
                break;
            case "Orina":
                labs.getUrine().addAll(filePaths);
                break;
            case "Antitetanus":
                labs.getAntitetanus().addAll(filePaths);
                break;
            case "CTX":
                labs.getCtx().addAll(filePaths);
                break;
            default:
                throw new IllegalArgumentException("Tipo de laboratorio inválido: " + labType);
        }

        labsRepository.save(labs);
    }

    @Override
    public LabsResponseDto getAllLabsByPatientId(Long patientId) {
        Labs labs = labsRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el laboratorio para el paciente."));
        return labsMapper.toDto(labs);
    }

    @Transactional
    @Override
    public void updateLabsObservations(Long patientId, LabsObservationsRequestDto dto) {
        Labs labs = labsRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el laboratorio para el paciente."));

        labsMapper.updateEntity(labs, dto);
        labsRepository.save(labs);
    }
}
