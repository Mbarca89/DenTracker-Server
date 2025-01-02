package com.mbarca89.DenTracker.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.mbarca89.DenTracker.context.ClientContext;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Aquí obtenemos el clientId del contexto actual
        return ClientContext.getClientId();
    }
}
