package com.mbarca89.DenTracker.service.main.impl;

import com.mbarca89.DenTracker.entity.client.ClientUser;
import com.mbarca89.DenTracker.entity.main.Client;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.client.ClientUserRepository;
import com.mbarca89.DenTracker.repository.main.FeatureRepository;
import com.mbarca89.DenTracker.service.main.PlanFeatureCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanFeatureCheckerServiceImpl implements PlanFeatureCheckerService {

    private final ClientUserRepository clientUserRepository;
    private final FeatureRepository featureRepository;

    /**
     * Chequea si el cliente (dueño del ClientUser autenticado) tiene habilitado el feature.
     */
    public boolean hasFeature(Long clientUserId, String featureCode) {
        ClientUser clientUser = clientUserRepository.findById(clientUserId)
                .orElseThrow(() -> new ResourceNotFoundException("ClientUser no encontrado: " + clientUserId));

        Client client = clientUser.getClient();

        return client.getPlan().getFeatures().stream()
                .anyMatch(feature -> feature.getCode().equals(featureCode));
    }
}
