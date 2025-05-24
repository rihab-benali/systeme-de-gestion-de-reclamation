package tn.itbs.systemeGestionReclamation.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Long clientId;    // Will be set for clients
    private final Long agentId;     // Will be set for agents

    public CustomUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long clientId,
            Long agentId
    ) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.clientId = clientId;
        this.agentId = agentId;
    }

    // Standard UserDetails methods...
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // Custom getters
    public Long getClientId() { return clientId; }
    public Long getAgentId() { return agentId; }
}