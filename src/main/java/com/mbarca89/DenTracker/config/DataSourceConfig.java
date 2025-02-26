package com.mbarca89.DenTracker.config;

import com.mbarca89.DenTracker.datasource.DynamicDataSourceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final String DB_URL_TEMPLATE = "jdbc:postgresql://localhost:5432/%s"; // Usamos la base de datos dinámica aquí

    @Bean
    public DynamicDataSourceImpl dynamicDataSource() {
        DynamicDataSourceImpl dynamicDataSource = new DynamicDataSourceImpl();

        // Usamos Map<Object, Object> para coincidir con el tipo esperado por AbstractRoutingDataSource
        Map<Object, Object> dataSourceMap = new HashMap<>();

        // Puedes agregar varios dataSources aquí según el clientId
        dataSourceMap.put("default", createDataSource("denTracker"));
        // Añadir más DataSources si es necesario para diferentes clientes

        dynamicDataSource.addDataSource("default", createDataSource("denTracker")); // Base de datos por defecto
        dynamicDataSource.setTargetDataSources(dataSourceMap);  // Configurar los DataSources
        dynamicDataSource.setDefaultTargetDataSource(createDataSource("denTracker"));  // Default DataSource

        return dynamicDataSource;
    }

    // Creamos una fuente de datos en función del clientId
    private DriverManagerDataSource createDataSource(String dbName) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String dbUrl = String.format(DB_URL_TEMPLATE, dbName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername("mbarca89");
        dataSource.setPassword("phoenixrules1A_");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.mbarca89.DenTracker.entity.main"); // Paquete de las entidades JPA

        // Configuración de Hibernate
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        // Propiedades adicionales de JPA
        Map<String, Object> jpaPropertiesMap = new HashMap<>();
        jpaPropertiesMap.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaPropertiesMap.put("hibernate.show_sql", "true");
        jpaPropertiesMap.put("hibernate.format_sql", "true");
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "update");

        // Convertir el Map a Properties
        Properties jpaProperties = new Properties();
        jpaProperties.putAll(jpaPropertiesMap);

        factoryBean.setJpaProperties(jpaProperties);

        return factoryBean;
    }


}
