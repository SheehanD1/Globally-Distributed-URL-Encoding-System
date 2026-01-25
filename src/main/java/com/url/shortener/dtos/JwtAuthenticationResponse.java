package com.url.shortener.dtos;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
//    private String type = "Bearer";
//    private Long id;
//    private String username;
//    private String email;
//    private List<String> roles;

    public JwtAuthenticationResponse(String accessToken) {
        this.token = accessToken;
    }
}
