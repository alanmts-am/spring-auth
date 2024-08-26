package br.com.alan.dto;

public record AuthResponse(String token, Long expiration) {
}
