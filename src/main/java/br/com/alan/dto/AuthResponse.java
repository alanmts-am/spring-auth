package br.com.alan.dto;

import java.util.Date;

public class AuthResponse {

    private String token;
    private Date expiration;

    public AuthResponse(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiration() {
        return expiration;
    }

}
