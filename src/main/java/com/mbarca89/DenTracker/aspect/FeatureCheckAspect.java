package com.mbarca89.DenTracker.aspect;

import com.mbarca89.DenTracker.exception.FeatureNotAllowedException;
import com.mbarca89.DenTracker.security.RequiresFeature;
import com.mbarca89.DenTracker.service.main.PlanFeatureCheckerService;
import com.mbarca89.DenTracker.utils.SessionUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureCheckAspect {

    private final PlanFeatureCheckerService planFeatureCheckerService;

    @Around("@annotation(com.mbarca89.DenTracker.security.RequiresFeature) || @within(com.mbarca89.DenTracker.security.RequiresFeature)")
    public Object checkFeature(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. Determinar el feature requerido
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RequiresFeature annotation = method.getAnnotation(RequiresFeature.class);
        if (annotation == null) {
            // Si no está en el método, la busco en la clase
            annotation = joinPoint.getTarget().getClass().getAnnotation(RequiresFeature.class);
        }

        if (annotation != null) {
            String featureCode = annotation.value();

            // 2. Obtener el clientUserId autenticado desde SessionUtils
            Long clientUserId = SessionUtils.getAuthenticatedClientUserId();
            if (clientUserId == null) {
                throw new IllegalStateException("No hay usuario autenticado.");
            }

            // 3. Chequear si el cliente tiene habilitado ese feature
            boolean allowed = planFeatureCheckerService.hasFeature(clientUserId, featureCode);

            if (!allowed) {
                throw new FeatureNotAllowedException(featureCode);
            }
        }

        // 4. Continuar la ejecución normal
        return joinPoint.proceed();
    }
}
