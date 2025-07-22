package com.balai.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    public Map<String, Object> validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                userServiceUrl + "/api/auth/validate",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return response.getBody();
    }
}
