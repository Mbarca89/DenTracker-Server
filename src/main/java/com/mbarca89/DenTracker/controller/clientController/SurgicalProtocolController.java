package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.SurgicalProtocolRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolListResponseDto;
import com.mbarca89.DenTracker.dto.response.patient.SurgicalProtocolResponseDto;
import com.mbarca89.DenTracker.service.patient.SurgicalProtocolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/surgical-protocol")
@RequiredArgsConstructor
public class SurgicalProtocolController {

    private final SurgicalProtocolService surgicalProtocolService;

    @PostMapping
    public ResponseEntity<SurgicalProtocolResponseDto> createSurgicalProtocol(
            @RequestParam Long patientId,
            @RequestBody SurgicalProtocolRequestDto dto) {
        SurgicalProtocolResponseDto response = surgicalProtocolService.createSurgicalProtocol(dto, patientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SurgicalProtocolListResponseDto>> getAllByPatient(@RequestParam Long patientId) {
        return ResponseEntity.ok(surgicalProtocolService.getAllByPatientId(patientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurgicalProtocolResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surgicalProtocolService.getById(id));
    }
}
