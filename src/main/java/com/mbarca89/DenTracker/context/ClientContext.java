package com.mbarca89.DenTracker.context;

public class ClientContext {

    private static final ThreadLocal<String> clientIdThreadLocal = new ThreadLocal<>();

    // Establecer el clientId en el contexto actual
    public static void setClientId(String clientId) {
        clientIdThreadLocal.set(clientId);
    }

    // Obtener el clientId del contexto actual
    public static String getClientId() {
        String clientId = clientIdThreadLocal.get();
        if (clientId == null) {
            clientId = "default";
        }
        return clientId;
    }


    // Limpiar el contexto actual
    public static void clear() {
        clientIdThreadLocal.remove();
    }
}
