package br.com.alan.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alan.dto.AuthRequest;
import br.com.alan.dto.AuthResponse;
import br.com.alan.model.Role;
import br.com.alan.model.User;
import br.com.alan.repository.RoleRespository;
import br.com.alan.repository.UserRepository;
import br.com.alan.util.TokenUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtil tokenUtil;

    public User create(AuthRequest userRequest) {
        if (userRepository.existsByLogin(userRequest.login())) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setLogin(userRequest.login());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        Role role = this.roleRespository.findById(Role.Values.MEMBER.getRoleId()).get();
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) {
        var user = userRepository.findByLogin(authRequest.login());

        if (user.isEmpty() || !passwordEncoder.matches(authRequest.password(), user.get().getPassword())) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        return new AuthResponse(this.tokenUtil.generate(user.get()));
    }
}
