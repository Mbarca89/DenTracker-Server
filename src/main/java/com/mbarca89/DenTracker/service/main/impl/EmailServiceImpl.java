package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.service.main.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        String subject = "Verificá tu cuenta en DenTracker";
        String verificationUrl = "https://tusitio.com/verify?token=" + token;
        String body = "Gracias por registrarte. Verificá tu email haciendo clic en el siguiente enlace:\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
