package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.LabsObservationsRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.LabsResponseDto;
import com.mbarca89.DenTracker.entity.patient.Labs;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LabsMapper {
    LabsResponseDto toDto(Labs entity);

    void updateEntity(@MappingTarget Labs entity, LabsObservationsRequestDto dto);
}
