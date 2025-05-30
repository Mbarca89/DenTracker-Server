package com.mbarca89.DenTracker.service.client.impl;

import com.mbarca89.DenTracker.dto.request.client.AppointmentRequestDto;
import com.mbarca89.DenTracker.dto.response.client.AppointmentResponseDto;
import com.mbarca89.DenTracker.entity.client.Appointment;
import com.mbarca89.DenTracker.mapper.client.AppointmentMapper;
import com.mbarca89.DenTracker.repository.client.AppointmentRepository;
import com.mbarca89.DenTracker.service.client.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentResponseDto create(AppointmentRequestDto dto, Long clientId) {
        Appointment appointment = appointmentMapper.toEntity(dto);
        appointment.setClientId(clientId);
        appointment.setMessageSent(false);
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentResponseDto> getAllByClientId(Long clientId) {
        return appointmentRepository.findByClientId(clientId).stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id, Long clientId) {
        Appointment appointment = appointmentRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado o no pertenece al cliente"));
        appointmentRepository.delete(appointment);
    }

    @Override
    public AppointmentResponseDto getById(Long id, Long clientId) {
        Appointment appointment = appointmentRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado o no pertenece al cliente"));
        return appointmentMapper.toDto(appointment);
    }
}
