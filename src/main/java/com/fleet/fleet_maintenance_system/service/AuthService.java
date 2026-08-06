package com.fleet.fleet_maintenance_system.service;

import com.fleet.fleet_maintenance_system.dto.AuthRequest;
import com.fleet.fleet_maintenance_system.dto.AuthResponse;
import com.fleet.fleet_maintenance_system.entity.Role;
import com.fleet.fleet_maintenance_system.entity.User;
import com.fleet.fleet_maintenance_system.repository.UserRepository;
import com.fleet.fleet_maintenance_system.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRequest authRequest, Role role){
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()){
                throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setRole(role);
        user.setPasswordHash(encoder.encode(authRequest.getPassword()));
        userRepository.save(user);

       String token = jwtUtil.generateToken(authRequest.getUsername(), role.name());

       return new AuthResponse(authRequest.getUsername(), token, role.name());
    }

    public  AuthResponse login(AuthRequest authRequest){

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("Invalid username or password"));

        if (!encoder.matches(password, user.getPasswordHash())){
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(username, user.getRole().toString());

        return new AuthResponse(username, token, user.getRole().name());
    }
}
