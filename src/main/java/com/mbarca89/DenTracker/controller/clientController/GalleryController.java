package com.mbarca89.DenTracker.controller.patient;

import com.mbarca89.DenTracker.dto.response.patient.GalleryResponseDto;
import com.mbarca89.DenTracker.service.patient.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clients/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam Long patientId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("imageName") String imageName
    ) throws Exception {
        galleryService.uploadImage(patientId, imageName, file);
        return ResponseEntity.ok("Imagen cargada correctamente para " + imageName);
    }

    @GetMapping("/thumbs")
    public ResponseEntity<GalleryResponseDto> getAllThumbsByPatientId(
            @RequestParam Long patientId,
            @RequestParam String study
    ) {
        GalleryResponseDto thumbsDto = galleryService.getAllThumbsByPatientId(patientId, study);
        return ResponseEntity.ok(thumbsDto);
    }

    @GetMapping("/singleGallery")
    public ResponseEntity<GalleryResponseDto> getSingleGalleryByPatientId(
            @RequestParam Long patientId,
            @RequestParam String study
    ) {
        GalleryResponseDto galleryDto = galleryService.getSingleGalleryByPatientId(patientId, study);
        return ResponseEntity.ok(galleryDto);
    }
}
