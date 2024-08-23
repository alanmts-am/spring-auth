package br.com.alan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alan.dto.AuthRequest;
import br.com.alan.model.User;
import br.com.alan.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Supondo que você tenha um repositório JPA para gerenciar usuários

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(AuthRequest userRequest) {
        // Verifica se o usuário já existe
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        // Cria uma nova entidade User
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Codifica a senha

        // Salva o novo usuário no banco de dados
        userRepository.save(user);
    }
}
