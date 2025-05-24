package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.beans.*;
import tn.itbs.systemeGestionReclamation.DTOs.RegisterRequest;
import tn.itbs.systemeGestionReclamation.DTOs.AuthResponse;
import tn.itbs.systemeGestionReclamation.repositories.UserRepository;
import tn.itbs.systemeGestionReclamation.util.CustomUserDetails;
import tn.itbs.systemeGestionReclamation.util.JwtUtil;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse registerClient(RegisterRequest request) {
        // Create and save User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);

        // Create and associate Client
        Client client = new Client();
        client.setNom(request.getFullName());
        client.setTelephone(request.getPhone());
        client.setEmail(request.getEmail());
        client.setUser(user);
        user.setClient(client);

        user = userRepository.save(user);

        // Create authentication response
        CustomUserDetails userDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                user.getClient().getId(),  // Client ID
                null                       // Agent ID (null for clients)
        );

        String token = jwtUtil.generateToken(userDetails, user.getRole().name());

        return new AuthResponse(
                token,
                user.getRole().name(),
                user.getClient().getId(),  // Client ID
                null                       // Agent ID
        );
    }
}