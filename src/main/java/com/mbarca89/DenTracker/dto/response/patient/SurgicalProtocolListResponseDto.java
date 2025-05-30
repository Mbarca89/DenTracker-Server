package com.mbarca89.DenTracker.dto.response.patient;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SurgicalProtocolListResponseDto {
    private Long id;
    private LocalDateTime date;
    private String firstAssistant;
    private String secondAssistant;
    private String type;
}
