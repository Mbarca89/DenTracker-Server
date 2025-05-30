package com.mbarca89.DenTracker.dto.response.client;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private Boolean messageSent;
}
