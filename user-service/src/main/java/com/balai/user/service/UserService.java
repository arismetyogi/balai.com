package com.balai.user.service;

import com.balai.user.exception.ResourceAlreadyExistedException;
import com.balai.user.model.dto.request.LoginRequest;
import com.balai.user.model.dto.response.AuthResponse;
import com.balai.user.model.dto.request.RegisterRequest;
import com.balai.user.model.entity.User;
import com.balai.user.model.enums.Role;
import com.balai.user.repository.UserRepository;
import com.balai.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistedException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistedException("Email already exists");
        }

        User userEntity = new User();
        userEntity.setUsername(request.getUsername());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRole(Role.USER);

        userRepository.save(userEntity);

        String token = jwtUtil.generateToken(userEntity);

        return new AuthResponse(token, userEntity.getUsername(), userEntity.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public User validateToken(String token) {
        System.out.println("Received token: " + token);
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
