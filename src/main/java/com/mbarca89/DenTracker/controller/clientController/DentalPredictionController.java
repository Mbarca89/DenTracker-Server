package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.DentalPredictionRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.DentalPredictionResponseDto;
import com.mbarca89.DenTracker.service.patient.DentalPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/dental-predictions")
@RequiredArgsConstructor
public class DentalPredictionController {

    private final DentalPredictionService dentalPredictionService;

    @GetMapping("/{patientId}")
    public ResponseEntity<DentalPredictionResponseDto> getByPatientId(@PathVariable Long patientId) {
        DentalPredictionResponseDto response = dentalPredictionService.getByPatientId(patientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<DentalPredictionResponseDto> updateDentalPrediction(
            @PathVariable Long patientId,
            @RequestBody DentalPredictionRequestDto dto) {
        DentalPredictionResponseDto updated = dentalPredictionService.updateDentalPrediction(patientId, dto);
        return ResponseEntity.ok(updated);
    }
}
