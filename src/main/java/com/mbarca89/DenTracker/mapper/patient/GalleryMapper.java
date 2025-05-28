package com.mbarca89.DenTracker.mapper.patient;

import com.mbarca89.DenTracker.dto.response.patient.GalleryResponseDto;
import com.mbarca89.DenTracker.entity.patient.Gallery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GalleryMapper {

    @Mapping(source = "patient.id", target = "patientId")
    GalleryResponseDto toDto(Gallery gallery);
}
