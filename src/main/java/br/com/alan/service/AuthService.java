package br.com.alan.service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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

    public void create(AuthRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.username())) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = new User();
        user.setUsername(userRequest.username());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        Role role = this.roleRespository.findById(Role.Values.MEMBER.getRoleId()).get();
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) {
        var user = userRepository.findByUsername(authRequest.username());

        if (user.isEmpty() || !passwordEncoder.matches(authRequest.password(), user.get().getPassword())) {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }

        return new AuthResponse(this.tokenUtil.generate(user.get()));
    }
}
