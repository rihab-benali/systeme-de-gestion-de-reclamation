package tn.itbs.systemeGestionReclamation.services;
import tn.itbs.systemeGestionReclamation.beans.Role;
import tn.itbs.systemeGestionReclamation.beans.User; // Ton entity personnalisée
import tn.itbs.systemeGestionReclamation.repositories.UserRepository; // Ton repository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tn.itbs.systemeGestionReclamation.util.CustomUserDetails;

import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Determine IDs based on role
        Long clientId = null;
        Long agentId = null;

        if (user.getRole() == Role.CLIENT && user.getClient() != null) {
            clientId = user.getClient().getId();
        } else if (user.getRole() == Role.AGENT_SAV && user.getAgentSAV() != null) {
            agentId = user.getAgentSAV().getId();
        }

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                clientId,
                agentId
        );
    }
}

