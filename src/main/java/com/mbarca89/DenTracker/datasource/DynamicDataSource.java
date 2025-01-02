package com.mbarca89.DenTracker.datasource;

import javax.sql.DataSource;

public interface DynamicDataSource {
    DataSource getDataSourceForClient(String clientId);
}
