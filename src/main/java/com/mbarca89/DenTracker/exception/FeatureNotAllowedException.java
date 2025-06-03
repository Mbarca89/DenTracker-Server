package com.mbarca89.DenTracker.exception;

public class FeatureNotAllowedException extends RuntimeException {
    public FeatureNotAllowedException(String featureCode) {
        super("Tu plan no incluye la caracteristica: " + featureCode);
    }
}
