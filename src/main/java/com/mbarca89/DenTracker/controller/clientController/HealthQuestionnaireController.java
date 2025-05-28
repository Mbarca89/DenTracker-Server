package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.HealthQuestionnaireRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.HealthQuestionnaireResponseDto;
import com.mbarca89.DenTracker.service.patient.HealthQuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/health-questionnaire")
@RequiredArgsConstructor
public class HealthQuestionnaireController {

    private final HealthQuestionnaireService service;

    @GetMapping("/{patientId}")
    public ResponseEntity<HealthQuestionnaireResponseDto> getByPatientId(@PathVariable Long patientId) {
        HealthQuestionnaireResponseDto response = service.getByPatientId(patientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<String> update(@PathVariable Long patientId, @RequestBody HealthQuestionnaireRequestDto dto) {
        service.update(patientId, dto);
        return ResponseEntity.status(HttpStatus.OK).body("Cuestionario de salud actualizado correctamente");
    }
}
