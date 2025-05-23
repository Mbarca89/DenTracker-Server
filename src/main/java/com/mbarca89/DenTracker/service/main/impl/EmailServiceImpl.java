package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.exception.EmailSendException;
import com.mbarca89.DenTracker.service.main.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${frontend.url}")
    private String frontendUrl;


    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) {
        try {
        String subject = "Verificá tu cuenta en DenTracker";
        String verificationUrl = frontendUrl + "/verify?token=" + token;
        String body = "Gracias por registrarte. Verificá tu email haciendo clic en el siguiente enlace:\n" + verificationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendException("No se pudo enviar el email de verificación.", e);
        }
    }

    public void sendPasswordResetEmail(String email, String url) {
        try {
            String subject = "Recuperación de contraseña";
            String body = "Hola,\n\nHacé clic en el siguiente enlace para restablecer tu contraseña:\n" + url +
                    "\n\nEste enlace expirará en 30 minutos.";

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendException("No se pudo enviar el email de recuperación.", e);
        }
    }


}
