package com.mbarca89.DenTracker.controller.publicController;

import com.mbarca89.DenTracker.dto.request.main.PasswordResetConfirmDto;
import com.mbarca89.DenTracker.dto.request.main.PasswordResetRequestDto;
import com.mbarca89.DenTracker.dto.response.main.AuthResponse;
import com.mbarca89.DenTracker.dto.request.main.LoginRequest;
import com.mbarca89.DenTracker.service.main.AuthService;
import com.mbarca89.DenTracker.service.main.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam("token") String token) {
        String result = authService.verifyClient(token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestBody @Valid PasswordResetRequestDto request) {
        passwordResetService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok().body(Map.of("message", "Se ha enviado un email con instrucciones para restablecer la contraseña."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetConfirmDto dto) {
        passwordResetService.resetPassword(dto.getToken(), dto.getNewPassword());
        return ResponseEntity.ok().body(Map.of("message", "La contraseña ha sido restablecida correctamente."));
    }

}

