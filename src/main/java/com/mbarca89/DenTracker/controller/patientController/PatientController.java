package com.mbarca89.DenTracker.controller.patientController;

import com.mbarca89.DenTracker.service.patient.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/clients/patients")
@CrossOrigin
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @DeleteMapping("/{id}/hard")
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> hardDeletePatient(
            @PathVariable Long id,
            HttpServletRequest request) {

        patientService.hardDeletePatient(id, request);
        return ResponseEntity.ok(Map.of("message", "Paciente eliminado definitivamente."));
    }

}
