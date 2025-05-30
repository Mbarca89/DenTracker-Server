package com.mbarca89.DenTracker.mapper.client;

import com.mbarca89.DenTracker.dto.request.client.AppointmentRequestDto;
import com.mbarca89.DenTracker.dto.response.client.AppointmentResponseDto;
import com.mbarca89.DenTracker.entity.client.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    Appointment toEntity(AppointmentRequestDto dto);
    AppointmentResponseDto toDto(Appointment appointment);
}
