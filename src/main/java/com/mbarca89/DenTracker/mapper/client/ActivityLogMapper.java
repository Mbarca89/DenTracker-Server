package com.mbarca89.DenTracker.mapper.client;

import com.mbarca89.DenTracker.dto.response.client.ActivityLogResponseDto;
import com.mbarca89.DenTracker.entity.client.ActivityLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityLogMapper {

    ActivityLogMapper INSTANCE = Mappers.getMapper(ActivityLogMapper.class);

    List<ActivityLogResponseDto> toResponseDtoList(List<ActivityLog> activityLogs);
}
