package com.mbarca89.DenTracker.dto.response.patient;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GalleryResponseDto {
    private Long id;
    private Long patientId;
    private Map<String, List<String>> images;
}
