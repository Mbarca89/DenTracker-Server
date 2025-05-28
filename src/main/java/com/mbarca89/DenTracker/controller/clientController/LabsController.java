package com.mbarca89.DenTracker.controller.clientController;

import com.mbarca89.DenTracker.dto.request.patient.LabsObservationsRequestDto;
import com.mbarca89.DenTracker.dto.response.patient.LabsResponseDto;
import com.mbarca89.DenTracker.service.patient.LabsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clients/labs")
@RequiredArgsConstructor
public class LabsController {

    private final LabsService labsService;

    // ✅ Subir archivos de laboratorio
    @PostMapping("/upload")
    public ResponseEntity<String> uploadLabFiles(
            @RequestParam Long patientId,
            @RequestParam String labType,
            @RequestParam MultipartFile[] files) {

        labsService.uploadLabFiles(patientId, labType, files);
        return ResponseEntity.ok("Archivos cargados correctamente para: " + labType);
    }

    // ✅ Obtener todos los datos de laboratorio de un paciente
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<LabsResponseDto> getAllLabsByPatientId(@PathVariable Long patientId) {
        LabsResponseDto responseDto = labsService.getAllLabsByPatientId(patientId);
        return ResponseEntity.ok(responseDto);
    }

    // ✅ Actualizar solo las observaciones
    @PutMapping("/observations/{patientId}")
    public ResponseEntity<String> updateLabsObservations(
            @PathVariable Long patientId,
            @RequestBody LabsObservationsRequestDto requestDto) {

        labsService.updateLabsObservations(patientId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Observaciones actualizadas correctamente.");
    }
}
