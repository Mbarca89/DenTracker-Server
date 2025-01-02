package com.mbarca89.DenTracker.datasource;

import com.mbarca89.DenTracker.context.ClientContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSourceImpl extends AbstractRoutingDataSource {

    private Map<String, DataSource> dataSourceMap = new HashMap<>();

    public void addDataSource(String clientId, DataSource dataSource) {
        dataSourceMap.put(clientId, dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // Si el clientId no está presente (por ejemplo, durante la creación de una nueva cuenta)
        // devolver un valor especial que le indique al DynamicDataSource que use el DataSource general
        String clientId = ClientContext.getClientId(); // Método que obtiene el clientId del contexto (por ejemplo, del JWT)

        if (clientId == null || clientId.isEmpty()) {
            return "default"; // Valor que indica que debe usarse el DataSource predeterminado
        }

        return clientId;
    }

    @Override
    public DataSource determineTargetDataSource() {
        String clientId = (String) determineCurrentLookupKey();
        return dataSourceMap.get(clientId);
    }

    @Override
    public void afterPropertiesSet() {
        if (dataSourceMap.isEmpty()) {
            throw new IllegalStateException("No DataSources configured in DynamicDataSourceImpl.");
        }

        // Configurar un DataSource predeterminado si no se encuentra el clientId
        DataSource defaultDataSource = dataSourceMap.values().iterator().next();
        setDefaultTargetDataSource(defaultDataSource);
        setTargetDataSources(new HashMap<>(dataSourceMap));
        super.afterPropertiesSet();
    }

}
