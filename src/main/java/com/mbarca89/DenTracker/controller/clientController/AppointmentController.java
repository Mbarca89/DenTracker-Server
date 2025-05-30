package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.client.AppointmentRequestDto;
import com.mbarca89.DenTracker.dto.response.client.AppointmentResponseDto;
import com.mbarca89.DenTracker.service.main.JwtService;
import com.mbarca89.DenTracker.service.client.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody AppointmentRequestDto dto,
                                                         HttpServletRequest request) {
        Long clientId = jwtService.getClientIdAsLong(jwtService.extractTokenFromRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.create(dto, clientId));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAll(HttpServletRequest request) {
        Long clientId = jwtService.getClientIdAsLong(jwtService.extractTokenFromRequest(request));
        return ResponseEntity.ok(appointmentService.getAllByClientId(clientId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long clientId = jwtService.getClientIdAsLong(jwtService.extractTokenFromRequest(request));
        appointmentService.delete(id, clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getById(@PathVariable Long id, HttpServletRequest request) {
        Long clientId = jwtService.getClientIdAsLong(jwtService.extractTokenFromRequest(request));
        return ResponseEntity.ok(appointmentService.getById(id, clientId));
    }

}
