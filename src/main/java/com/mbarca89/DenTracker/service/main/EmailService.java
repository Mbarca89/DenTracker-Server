package com.mbarca89.DenTracker.service.main;

public interface EmailService {
    void sendVerificationEmail(String to, String token);
}
