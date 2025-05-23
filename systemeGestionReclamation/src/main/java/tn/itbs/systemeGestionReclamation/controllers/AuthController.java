package tn.itbs.systemeGestionReclamation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.itbs.systemeGestionReclamation.beans.User;
import tn.itbs.systemeGestionReclamation.beans.Role;
import tn.itbs.systemeGestionReclamation.services.AuthService;
import tn.itbs.systemeGestionReclamation.services.CustomUserDetailsService;
import tn.itbs.systemeGestionReclamation.util.JwtUtil;
import org.springframework.security.core.GrantedAuthority;
import tn.itbs.systemeGestionReclamation.DTOs.RegisterRequest;
import tn.itbs.systemeGestionReclamation.DTOs.LoginRequest;
import tn.itbs.systemeGestionReclamation.DTOs.AuthResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (request.getRole() == Role.CLIENT) {
            AuthResponse response = authService.registerClient(request);
            return ResponseEntity.ok(response);
        }
        // Optionally handle AGENT or ADMIN roles differently
        return ResponseEntity.badRequest().body("Only CLIENT registration is allowed here.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        // Note: extractRoleFromUserDetails is not defined in JwtUtil; using a workaround
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .findFirst()
                .map(auth -> auth.replace("ROLE_", ""))
                .orElse("USER");
        String token = jwtUtil.generateToken(userDetails, role);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}





