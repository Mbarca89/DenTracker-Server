package com.mbarca89.DenTracker.security;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresFeature {
    /**
     * Código del feature que se requiere.
     * Ejemplo: "AGENDA", "PATIENT_HISTORY", etc.
     */
    String value();
}
