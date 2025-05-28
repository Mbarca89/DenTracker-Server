package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.MedicalHistoryRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.MedicalHistoryResponseDto;
import com.mbarca89.DenTracker.service.patient.MedicalHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {

    private final MedicalHistoryService medicalHistoryService;

    @GetMapping("/{patientId}")
    public ResponseEntity<MedicalHistoryResponseDto> getByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalHistoryService.getByPatientId(patientId));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<Void> update(@PathVariable Long patientId,
                                       @RequestBody MedicalHistoryRequestDto dto) {
        medicalHistoryService.update(patientId, dto);
        return ResponseEntity.noContent().build();
    }
}
