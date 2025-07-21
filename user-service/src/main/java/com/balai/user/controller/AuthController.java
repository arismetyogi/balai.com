package com.balai.user.controller;

import com.balai.user.model.dto.request.LoginRequest;
import com.balai.user.model.dto.request.RegisterRequest;
import com.balai.user.model.dto.response.ApiResponse;
import com.balai.user.model.dto.response.AuthResponse;
import com.balai.user.model.entity.User;
import com.balai.user.model.repository.UserRepository;
import com.balai.user.model.service.UserService;
import com.balai.user.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ResponseUtil.success("User registered successfully", userService.register(request), null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        System.out.println("Login username: " + request.getUsername());
        System.out.println("User in repo? " + userRepository.findByUsername(request.getUsername()).isPresent());
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<User> validateToken(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Remove "Bearer " prefix
        User user = userService.validateToken(jwtToken);
        return ResponseEntity.ok(user);
    }
}
