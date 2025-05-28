package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.PatientRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.PatientResponseDto;
import com.mbarca89.DenTracker.service.patient.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients/patients")
@CrossOrigin
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@RequestBody PatientRequestDto request,
                                                            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(patientService.createPatient(request, httpRequest));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(HttpServletRequest request) {
        return ResponseEntity.ok(patientService.getAllPatients(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id,
                                                             HttpServletRequest request) {
        return ResponseEntity.ok(patientService.getPatientById(id, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id,
                                           @RequestBody PatientRequestDto dto,
                                           HttpServletRequest request) {
        patientService.updatePatient(id, dto, request);
        return ResponseEntity.ok(Map.of("message", "Paciente actualizado correctamente."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeletePatient(@PathVariable Long id, HttpServletRequest request) {
        patientService.deletePatient(id, request);
        return ResponseEntity.ok(Map.of("message", "Paciente eliminado correctamente."));
    }

    @PutMapping("/{id}/transfer")
    public ResponseEntity<?> transferPatient(@PathVariable Long id,
                                             @RequestParam Long targetUserId,
                                             HttpServletRequest request) {
        patientService.transferPatient(id, targetUserId, request);
        return ResponseEntity.ok(Map.of("message", "Paciente transferido correctamente."));
    }

    @DeleteMapping("/{id}/hard")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> hardDeletePatient(
            @PathVariable Long id,
            HttpServletRequest request) {

        patientService.hardDeletePatient(id, request);
        return ResponseEntity.ok(Map.of("message", "Paciente eliminado definitivamente."));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<PatientResponseDto>> getMyPatients(HttpServletRequest request) {
        return ResponseEntity.ok(patientService.getPatientsByUser(request));
    }


}
