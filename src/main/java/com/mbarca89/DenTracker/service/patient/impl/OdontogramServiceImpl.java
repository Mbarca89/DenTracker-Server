package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.OdontogramRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.OdontogramResponseDto;
import com.mbarca89.DenTracker.entity.patient.Odontogram;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.OdontogramMapper;
import com.mbarca89.DenTracker.repository.patient.OdontogramRepository;
import com.mbarca89.DenTracker.repository.patient.PatientRepository;
import com.mbarca89.DenTracker.service.patient.OdontogramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OdontogramServiceImpl implements OdontogramService {

    private final OdontogramRepository odontogramRepository;
    private final PatientRepository patientRepository;
    private final OdontogramMapper odontogramMapper;

    @Override
    public void createOdontogram(Long patientId, OdontogramRequestDto dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        Odontogram odontogram = odontogramMapper.toEntity(dto);
        odontogram.setPatient(patient);
        odontogram.setOdontogramDate(LocalDateTime.now());
        odontogramRepository.save(odontogram);
    }

    @Override
    public OdontogramResponseDto getLastOdontogram(Long patientId) {
        Odontogram odontogram = odontogramRepository.findTopByPatientIdOrderByOdontogramDateDesc(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Aún no se ha guardado ningún odontograma"));
        return odontogramMapper.toDto(odontogram);
    }

    @Override
    public OdontogramResponseDto getOdontogramById(Long id) {
        Odontogram odontogram = odontogramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontograma no encontrado"));
        return odontogramMapper.toDto(odontogram);
    }

    @Override
    public List<OdontogramResponseDto> getOdontogramsByPatientId(Long patientId) {
        List<Odontogram> odontograms = odontogramRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Hubo un error al obtener los odontogramas"));
        return odontograms.stream().map(odontogramMapper::toDto).toList();
    }
}