package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.DentalEvaluationRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.DentalEvaluationResponseDto;
import com.mbarca89.DenTracker.service.patient.DentalEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/dental-evaluation")
@RequiredArgsConstructor
public class DentalEvaluationController {

    private final DentalEvaluationService dentalEvaluationService;

    @GetMapping("/{patientId}")
    public ResponseEntity<DentalEvaluationResponseDto> getByPatientId(@PathVariable Long patientId) {
        DentalEvaluationResponseDto response = dentalEvaluationService.getByPatientId(patientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<DentalEvaluationResponseDto> updateDentalEvaluation(
            @PathVariable Long patientId,
            @RequestBody DentalEvaluationRequestDto dto) {
        DentalEvaluationResponseDto updated = dentalEvaluationService.updateDentalEvaluation(patientId, dto);
        return ResponseEntity.ok(updated);
    }
}
