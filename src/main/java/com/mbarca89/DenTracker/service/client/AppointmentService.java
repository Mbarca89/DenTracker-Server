package com.mbarca89.DenTracker.service.client;

import com.mbarca89.DenTracker.dto.request.client.AppointmentRequestDto;
import com.mbarca89.DenTracker.dto.response.client.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto create(AppointmentRequestDto dto, Long clientId);
    List<AppointmentResponseDto> getAllByClientId(Long clientId);
    void delete(Long id, Long clientId);
    AppointmentResponseDto getById(Long id, Long clientId);
}
