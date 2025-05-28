package com.mbarca89.DenTracker.utils;

import com.mbarca89.DenTracker.entity.base.AuditableEntity;
import com.mbarca89.DenTracker.service.main.JwtService;
import jakarta.servlet.http.HttpServletRequest;

public class AuditHelper {

    public static void setAuditFields(AuditableEntity entity, HttpServletRequest request, JwtService jwtService) {
        Long clientId = SessionUtils.getClientId(request, jwtService);
        Long createdByUserId = SessionUtils.getAuthenticatedClientUserId();

        if (clientId != null) {
            entity.setClientId(clientId);
        }

        if (createdByUserId != null) {
            entity.setCreatedByUserId(createdByUserId);
        }

        entity.setActive(true);
    }
}
