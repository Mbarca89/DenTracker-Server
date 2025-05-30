package com.mbarca89.DenTracker.service.patient.impl;

import com.mbarca89.DenTracker.dto.request.patient.SurgicalProtocolRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolListResponseDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolResponseDto;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.entity.patient.SurgicalProtocol;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.mapper.patient.SurgicalProtocolMapper;
import com.mbarca89.DenTracker.repository.patient.PatientRepository;
import com.mbarca89.DenTracker.repository.patient.SurgicalProtocolRepository;
import com.mbarca89.DenTracker.service.patient.SurgicalProtocolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurgicalProtocolServiceImpl implements SurgicalProtocolService {

    private final SurgicalProtocolRepository surgicalProtocolRepository;
    private final PatientRepository patientRepository;
    private final SurgicalProtocolMapper surgicalProtocolMapper;

    @Override
    public SurgicalProtocolResponseDto createSurgicalProtocol(SurgicalProtocolRequestDto dto, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        SurgicalProtocol surgicalProtocol = surgicalProtocolMapper.toEntity(dto);
        surgicalProtocol.setPatient(patient);

        SurgicalProtocol saved = surgicalProtocolRepository.save(surgicalProtocol);
        return surgicalProtocolMapper.toDto(saved);
    }

    @Override
    public List<SurgicalProtocolListResponseDto> getAllByPatientId(Long patientId) {
        List<SurgicalProtocol> protocols = surgicalProtocolRepository.findByPatientId(patientId);
        return surgicalProtocolMapper.toListDto(protocols);
    }

    @Override
    public SurgicalProtocolResponseDto getById(Long id) {
        SurgicalProtocol protocol = surgicalProtocolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Protocolo no encontrado"));
        return surgicalProtocolMapper.toDto(protocol);
    }
}
