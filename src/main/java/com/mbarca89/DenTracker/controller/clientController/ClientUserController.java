package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.mapper.client.ClientUserMapper;
import com.mbarca89.DenTracker.service.client.ClientUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/users")
@RequiredArgsConstructor
public class ClientUserController {

    private final ClientUserService clientUserService;
    private final ClientUserMapper clientUserMapper;


    @PostMapping
    public ResponseEntity<ClientUserResponseDto> createClientUser(@RequestBody ClientUserCreateRequestDto request,
                                                                  @AuthenticationPrincipal Client authenticatedClient) {
        ClientUser created = clientUserService.createClientUser(request, authenticatedClient);
        ClientUserResponseDto dto = clientUserMapper.toDto(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping
    public ResponseEntity<List<ClientUserResponseDto>> getClientUsers(
            @AuthenticationPrincipal Client authenticatedClient) {

        List<ClientUserResponseDto> users = clientUserService.getActiveClientUsers(authenticatedClient.getId());
        return ResponseEntity.ok(users);
    }


}
