package com.alkemy.java2.authsecurity.service.impl;


import com.alkemy.java2.authsecurity.dto.AuthRequest;
import com.alkemy.java2.authsecurity.dto.AuthResponse;
import com.alkemy.java2.authsecurity.dto.RegisterRequest;
import com.alkemy.java2.model.Role;
import com.alkemy.java2.model.User;
import com.alkemy.java2.authsecurity.service.AuthService;
import com.alkemy.java2.authsecurity.service.JwtService;
import com.alkemy.java2.exception.UserAlreadyExistsException;
import com.alkemy.java2.mapper.UserMapper;
import com.alkemy.java2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j // Lombok annotation for logger
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        log.debug("Intentando registrar nuevo usuario: {}", request.getUsername());


        // Changed to use our custom exists method
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username {} already exists", request.getUsername());
            throw new UserAlreadyExistsException("Username already exists");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        User savedUser = userRepository.save(user);
        log.info("Nuevo usuario registrado con ID: {}", savedUser.getId());

        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        log.debug("Autenticando usuario: {}", request.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> {
                        log.error("Usuario no encontrado después de autenticación exitosa: {}", request.getUsername());
                        return new UsernameNotFoundException("User not found");
                    });

            String jwtToken = jwtService.generateToken(user);
            log.info("Usuario {} autenticado exitosamente", user.getUsername());
            user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(authority -> log.info("User authority: {}", authority));

            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            log.warn("Falló autenticación para usuario: {}", request.getUsername());
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}