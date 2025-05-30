package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.OdontogramRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.OdontogramResponseDto;
import com.mbarca89.DenTracker.entity.patient.Odontogram;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OdontogramMapper {

    Odontogram toEntity(OdontogramRequestDto dto);

    OdontogramResponseDto toDto(Odontogram odontogram);
}
