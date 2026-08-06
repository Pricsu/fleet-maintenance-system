package com.fleet.fleet_maintenance_system.controller;

import com.fleet.fleet_maintenance_system.dto.AuthRequest;
import com.fleet.fleet_maintenance_system.dto.AuthResponse;
import com.fleet.fleet_maintenance_system.dto.WhoAmIResponse;
import com.fleet.fleet_maintenance_system.entity.Role;
import com.fleet.fleet_maintenance_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request){
        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRequest request,
                                 @RequestParam(defaultValue = "TECHNICIAN") Role role){
        return authService.register(request, role);
    }

    @GetMapping("/me")
    public WhoAmIResponse me(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
            String role = authentication.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(auth -> auth.substring(5))
                .orElse("UNKNOWN");
        return new WhoAmIResponse(username, role);
    }
}
