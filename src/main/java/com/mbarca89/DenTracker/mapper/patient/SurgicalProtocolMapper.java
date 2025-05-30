package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.SurgicalProtocolRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolListResponseDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolResponseDto;
import com.mbarca89.DenTracker.entity.patient.SurgicalProtocol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SurgicalProtocolMapper {
    SurgicalProtocol toEntity(SurgicalProtocolRequestDto dto);
    SurgicalProtocolResponseDto toDto(SurgicalProtocol entity);
    List<SurgicalProtocolListResponseDto> toListDto(List<SurgicalProtocol> protocols);
}
