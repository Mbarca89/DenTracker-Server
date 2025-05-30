package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.request.patient.WorkPlanRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.WorkPlanListResponseDto;
import com.mbarca89.DenTracker.dto.response.patient.WorkPlanResponseDto;
import com.mbarca89.DenTracker.entity.patient.WorkPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkPlanMapper {
    WorkPlan toEntity(WorkPlanRequestDto dto);

    WorkPlanResponseDto toDto(WorkPlan entity);

    List<WorkPlanResponseDto> toDtoList(List<WorkPlan> entities);

    @Mapping(target = "status", source = "status")
    WorkPlanListResponseDto toListDto(WorkPlan entity);

    List<WorkPlanListResponseDto> toListDtoList(List<WorkPlan> entities);

    void updateEntity(@MappingTarget WorkPlan entity, WorkPlanRequestDto dto);
}
