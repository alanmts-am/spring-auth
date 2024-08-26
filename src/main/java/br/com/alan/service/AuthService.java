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

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtEncoder jwtEncoder;

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

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("auth-jwt")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponse(jwtValue, expiresIn);
    }
}
