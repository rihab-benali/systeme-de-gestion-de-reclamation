package tn.itbs.systemeGestionReclamation.DTOs;

public class AuthResponse {
    private String token;
    private String role;
    private Long clientId;  // Only populated for clients
    private Long agentId;   // Only populated for agents

    // Single unified constructor
    public AuthResponse(String token, String role, Long clientId, Long agentId) {
        this.token = token;
        this.role = role;
        this.clientId = clientId;
        this.agentId = agentId;
    }

    // Factory methods for better readability
    public static AuthResponse forClient(String token, String role, Long clientId) {
        return new AuthResponse(token, role, clientId, null);
    }

    public static AuthResponse forAgent(String token, String role, Long agentId) {
        return new AuthResponse(token, role, null, agentId);
    }

    // Getters
    public String getToken() { return token; }
    public String getRole() { return role; }
    public Long getClientId() { return clientId; }
    public Long getAgentId() { return agentId; }
}

