package com.mbarca89.DenTracker.aspect;

import com.mbarca89.DenTracker.entity.client.ActivityLog;
import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.patient.Patient;
import com.mbarca89.DenTracker.repository.client.ActivityLogRepository;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final ActivityLogRepository activityLogRepository;
    private final ClientUserRepository clientUserRepository;

    @AfterReturning("execution(* com.mbarca89.DenTracker.service.*.*(..))")
    public void logActivity(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String activity = null;
        Long patientId = null;

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Long) {
                patientId = (Long) arg;
                break;
            }
        }

        if (patientId == null) return;

        Patient patient = new Patient();
        patient.setId(patientId);

        // Básico: solo logueamos los métodos "update", "create", "add", "close", "upload"
        if (methodName.startsWith("update")) activity = "Edición de información";
        else if (methodName.startsWith("create")) activity = "Creación de nuevo registro";
        else if (methodName.startsWith("add")) activity = "Nueva etapa agregada";
        else if (methodName.startsWith("close")) activity = "Plan finalizado";
        else if (methodName.startsWith("upload")) activity = "Archivo subido";

        if (activity != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = (auth != null) ? auth.getName() : "Anonymous";

            // Obtenemos el nombre real del usuario (campo "name" de ClientUser)
            String realName = clientUserRepository.findByName(username)
                    .map(ClientUser::getName)
                    .orElse("Anonymous");

            ActivityLog activityLog = new ActivityLog();
            activityLog.setActivity(activity);
            activityLog.setTimestamp(LocalDateTime.now());
            activityLog.setUsername(realName);
            activityLog.setPatient(patient);

            activityLogRepository.save(activityLog);
        }
    }
}
