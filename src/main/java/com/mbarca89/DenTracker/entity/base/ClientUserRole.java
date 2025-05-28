package com.mbarca89.DenTracker.entity.base;

public enum ClientUserRole {
    DENTIST("Odontólogo"),
    SECRETARY("Secretaria"),
    OWNER("Propietario");

    private final String displayName;

    ClientUserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


