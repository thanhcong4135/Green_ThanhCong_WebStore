package com.green.webstoreclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.AuthRequestDto;
import com.green.webstoremodels.dto.AuthResponseDto;
import com.green.webstoremodels.dto.RefreshRequestDto;
import com.green.webstoremodels.dto.UserDto;

@Component
public class AuthApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${auth.service.login-url:http://localhost:8090/api/auth/login}")
    private String loginUrl;

    @Value("${auth.service.me-url:http://localhost:8090/api/auth/me}")
    private String meUrl;

    @Value("${auth.service.refresh-url:http://localhost:8090/api/auth/refresh}")
    private String refreshUrl;

    @Value("${auth.service.users-url:http://localhost:8090/api/users}")
    private String usersUrl;

    public AuthResponseDto login(AuthRequestDto request) {
        return restTemplate.postForObject(loginUrl, request, AuthResponseDto.class);
    }

    public AuthResponseDto refresh(String refreshToken) {
        RefreshRequestDto req = new RefreshRequestDto();
        req.setRefreshToken(refreshToken);
        return restTemplate.postForObject(refreshUrl, req, AuthResponseDto.class);
    }

    public UserDto getProfile() {
        // Giả định có interceptor token, ở đây gọi trực tiếp
        return restTemplate.getForObject(meUrl, UserDto.class);
    }

    public UserDto findUserByUsername(String username) {
        String url = UriComponentsBuilder.fromHttpUrl(usersUrl)
                .pathSegment("by-username", username)
                .toUriString();
        return restTemplate.getForObject(url, UserDto.class);
    }
}
