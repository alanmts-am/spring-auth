package br.com.alan.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String authenticate() {
        return "token";
    }

}
