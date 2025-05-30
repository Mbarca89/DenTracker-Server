package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.OdontogramRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.OdontogramResponseDto;
import com.mbarca89.DenTracker.service.patient.OdontogramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/odontogram")
@RequiredArgsConstructor
public class OdontogramController {

    private final OdontogramService odontogramService;

    @PostMapping("/{patientId}")
    public ResponseEntity<Void> createOdontogram(@PathVariable Long patientId,
                                                 @RequestBody OdontogramRequestDto dto) {
        odontogramService.createOdontogram(patientId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{patientId}/last")
    public ResponseEntity<OdontogramResponseDto> getLastOdontogram(@PathVariable Long patientId) {
        return ResponseEntity.ok(odontogramService.getLastOdontogram(patientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontogramResponseDto> getOdontogramById(@PathVariable Long id) {
        return ResponseEntity.ok(odontogramService.getOdontogramById(id));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<OdontogramResponseDto>> getOdontogramsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(odontogramService.getOdontogramsByPatientId(patientId));
    }
}