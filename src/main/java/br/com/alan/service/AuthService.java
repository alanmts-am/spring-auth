package br.com.alan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alan.dto.AuthRequest;
import br.com.alan.dto.AuthResponse;
import br.com.alan.model.Role;
import br.com.alan.model.User;
import br.com.alan.repository.RoleRespository;
import br.com.alan.repository.UserRepository;
import br.com.alan.util.JwtTokenUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public void create(AuthRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        List<Role> roles = new ArrayList<>();

        Role role = this.roleRespository.findByRole("ROLE_MEMBER").get();
        roles.add(role);

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                        authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            final String token = jwtTokenUtil.generateToken(authentication.getName());
            return new AuthResponse(token, jwtTokenUtil.getClaimsFromToken(token).getExpiration());
        }

        return null;
    }
}
