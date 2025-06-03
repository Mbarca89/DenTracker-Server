package com.mbarca89.DenTracker.mapper.main;

import com.mbarca89.DenTracker.dto.response.main.PlanWithFeaturesResponseDto;
import com.mbarca89.DenTracker.entity.main.Feature;
import com.mbarca89.DenTracker.entity.main.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    @Mapping(target = "featureCodes", source = "features", qualifiedByName = "mapFeaturesToCodes")
    PlanWithFeaturesResponseDto toPlanWithFeaturesDto(Plan plan);

    @Named("mapFeaturesToCodes")
    default Set<String> mapFeaturesToCodes(Set<Feature> features) {
        return features.stream()
                .map(Feature::getCode)
                .collect(Collectors.toSet());
    }
}
