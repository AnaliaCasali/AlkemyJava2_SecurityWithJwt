package com.alkemy.java2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class UserController {

    @GetMapping
    public ResponseEntity<String> mensaje() {
        return ResponseEntity.ok("Hola, test endpoint!");
    }

    @GetMapping("/public")
    @Operation(summary = "Endpoint público")
    public ResponseEntity<String> publicEndpoint() {
        log.info("🔓 Acceso al endpoint público - IP: {}");
        return ResponseEntity.ok("Este es un endpoint público");
    }

    @GetMapping("/authenticated")
    @Operation(summary = "Endpoint para usuarios autenticados",
            security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<String> authenticatedEndpoint(Authentication authentication) {
        String username = authentication.getName();
        log.info("🔑 Usuario autenticado accediendo: {} - Roles: {} - IP: {}",
                username,
                authentication.getAuthorities());

        return ResponseEntity.ok("Hola " + username + ", este endpoint requiere autenticación");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Endpoint solo para administradores",
        security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<String> adminEndpoint(Authentication authentication,
                                                HttpServletRequest request) {
        String username = authentication.getName();
        log.warn("⚡ Acceso administrativo detectado - Usuario: {} - Roles: {} - IP: {}",
            username,
            authentication.getAuthorities(),
            request.getRemoteAddr());

        return ResponseEntity.ok("Bienvenido administrador " + username);
    }

}