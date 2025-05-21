package com.mbarca89.DenTracker.mapper.client;

import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientUserMapper {

    ClientUserResponseDto toDto(ClientUser clientUser);

    List<ClientUserResponseDto> toDtoList(List<ClientUser> clientUsers);
}
