package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.entity.main.PasswordResetToken;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.main.ClientRepository;
import com.mbarca89.DenTracker.repository.main.PasswordResetTokenRepository;
import com.mbarca89.DenTracker.service.main.EmailService;
import com.mbarca89.DenTracker.service.main.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final ClientRepository clientRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${frontend.url}")
    private String frontendUrl;


    public void requestPasswordReset(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un cliente con ese email."));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setClient(client);
        resetToken.setToken(token);
        resetToken.setExpiration(expiration);
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        String resetUrl = frontendUrl + "/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(email, resetUrl);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido."));

        if (resetToken.isUsed()) {
            throw new IllegalStateException("El token ya fue utilizado.");
        }

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("El token ha expirado.");
        }

        Client client = resetToken.getClient();
        String hashedPassword = passwordEncoder.encode(newPassword);
        client.setPassword(hashedPassword);
        clientRepository.save(client);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
    }

}
