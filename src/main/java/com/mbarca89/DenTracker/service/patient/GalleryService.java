package com.mbarca89.DenTracker.service.patient;

import com.mbarca89.DenTracker.dto.response.patient.GalleryResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface GalleryService {
    GalleryResponseDto getAllThumbsByPatientId(Long patientId, String study);
    GalleryResponseDto getSingleGalleryByPatientId(Long patientId, String study);
    void uploadImage(Long patientId, String imageName, MultipartFile file) throws Exception;
}
