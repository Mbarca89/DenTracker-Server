package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.client.ClientUserCreateRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserSelectionRequestDto;
import com.mbarca89.DenTracker.dto.request.client.ClientUserUpdateRequestDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserSelfResponseDto;
import com.mbarca89.DenTracker.dto.response.client.ClientUserTokenResponseDto;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.mapper.client.ClientUserMapper;
import com.mbarca89.DenTracker.service.client.ClientUserService;
import com.mbarca89.DenTracker.service.main.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients/users")
@RequiredArgsConstructor
public class ClientUserController {

    private final ClientUserService clientUserService;
    private final ClientUserMapper clientUserMapper;
    private final JwtService jwtService;

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

    @PutMapping("/{id}")
    public ResponseEntity<ClientUserResponseDto> updateClientUser(@PathVariable Long id,
                                                                  @RequestBody @Valid ClientUserUpdateRequestDto request,
                                                                  @AuthenticationPrincipal Client client) throws AccessDeniedException {
        ClientUserResponseDto dto = clientUserService.updateClientUser(id, request, client);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteClientUser(@PathVariable Long id,
                                                                @AuthenticationPrincipal Client client) throws AccessDeniedException {
        clientUserService.deleteClientUser(id, client);
        return ResponseEntity.ok(Map.of("message", "Subusuario eliminado correctamente."));
    }

    @GetMapping("/current")
    public ResponseEntity<ClientUserSelfResponseDto> getCurrentClientUser(Authentication authentication) {
        Long clientUserId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(clientUserService.getCurrentClientUser(clientUserId));
    }


    @PostMapping("/select")
    public ResponseEntity<ClientUserTokenResponseDto> selectClientUser(
            @AuthenticationPrincipal Client client,
            @RequestBody @Valid ClientUserSelectionRequestDto request) throws AccessDeniedException {

        return ResponseEntity.ok(clientUserService.authenticateClientUser(request, client));
    }

}
