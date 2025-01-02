package com.mbarca89.DenTracker.service.impl;

import com.mbarca89.DenTracker.datasource.DynamicDataSourceImpl;
import com.mbarca89.DenTracker.dto.ClientRequest;
import com.mbarca89.DenTracker.dto.ClientResponse;
import com.mbarca89.DenTracker.entity.Client;
import com.mbarca89.DenTracker.exception.ResourceNotFoundException;
import com.mbarca89.DenTracker.repository.ClientRepository;
import com.mbarca89.DenTracker.service.ClientService;
import com.mbarca89.DenTracker.util.CryptoUtils;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DynamicDataSourceImpl dynamicDataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CryptoUtils cryptoUtils;

    @Override
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron clientes.");
        }
        return clients.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse registerNewClient(ClientRequest request) throws NoSuchAlgorithmException {
        // Paso 1: Crear un nuevo cliente en la tabla clients
        Client client = mapToClient(request);
        client.setDatabaseUrl("jdbc:postgresql://localhost:5432/" + request.getClientName().replaceAll(" ", "_") + "_" + request.getClientSurname().replaceAll(" ", "_") + "_" + System.currentTimeMillis()); // URL de la base de datos
        client.setCreatedAt(LocalDateTime.now());

        // Guardar el cliente en la base de datos
        Client savedClient = clientRepository.save(client);

        // Paso 2: Crear la base de datos para el cliente
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/denTracker", // Conexión al servidor principal
                "mbarca89", // Usuario de PostgreSQL
                "phoenixrules1A_")) { // Contraseña de PostgreSQL

            // Crear la base de datos del cliente
            String createDatabaseQuery = "CREATE DATABASE \"" + savedClient.getDatabaseUrl() + "\"";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createDatabaseQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la base de datos para el cliente: " + e.getMessage());
        }

        // Paso 3: Configurar el DataSource para el cliente
        try {
            DataSource clientDataSource = new DriverManagerDataSource(
                    savedClient.getDatabaseUrl(), // URL de la base de datos del cliente
                    "mbarca89", // Cambiar por las credenciales de tu base de datos
                    "phoenixrules1A_" // Cambiar por las credenciales de tu base de datos
            );

            // Agregar el DataSource del cliente a la lista de fuentes de datos dinámicas
            dynamicDataSource.addDataSource(savedClient.getId().toString(), clientDataSource);
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar la base de datos para el cliente: " + e.getMessage());
        }
        return mapToResponse(savedClient);
    }

    private ClientResponse mapToResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setClientName(client.getClientName());
        response.setClientSurname(client.getClientSurname());
        response.setUsername(client.getUsername());
        response.setDatabaseUrl(client.getDatabaseUrl());
        response.setSubscriptionStatus(client.getSubscriptionStatus());
        response.setCreatedAt(client.getCreatedAt().toString());
        return response;
    }

    private Client mapToClient(ClientRequest clientRequest) throws NoSuchAlgorithmException {

        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(clientRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }

        String hashedPassword = passwordEncoder.encode(decryptedPassword);
        System.out.println("mapper: " + clientRequest.getClientSurname());
        Client client = new Client();
        if (clientRequest.getId() != null) client.setId(clientRequest.getId());
        client.setClientName(clientRequest.getClientName());
        client.setClientSurname(clientRequest.getClientSurname());
        client.setUsername(clientRequest.getUsername());
        client.setPassword(hashedPassword);
        client.setSubscriptionStatus(clientRequest.getSubscriptionStatus());
        client.setDatabaseUrl(clientRequest.getDatabaseUrl());
        return client;
    }
}
