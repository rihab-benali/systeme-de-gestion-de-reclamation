package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Import for authorities
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.beans.User;
import tn.itbs.systemeGestionReclamation.beans.Role;
import tn.itbs.systemeGestionReclamation.repositories.UserRepository;
import tn.itbs.systemeGestionReclamation.DTOs.RegisterRequest;
import tn.itbs.systemeGestionReclamation.DTOs.AuthResponse;
import tn.itbs.systemeGestionReclamation.beans.Client;
import tn.itbs.systemeGestionReclamation.util.JwtUtil;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse registerClient(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);

        Client client = new Client();
        client.setNom(request.getFullName());
        client.setTelephone(request.getPhone());
        client.setEmail(request.getEmail());
        client.setUser(user);
        user.setClient(client);

        userRepository.save(user);

        // Create UserDetails for the newly registered user
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(user.getRole().name()))
                .build();

        String token = jwtUtil.generateToken(userDetails, user.getRole().name());
        return new AuthResponse(token);
    }
}