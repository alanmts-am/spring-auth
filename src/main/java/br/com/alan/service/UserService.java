package br.com.alan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alan.dto.AuthRequest;
import br.com.alan.model.Role;
import br.com.alan.model.User;
import br.com.alan.repository.RoleRespository;
import br.com.alan.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(AuthRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("User already exists");
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
}
