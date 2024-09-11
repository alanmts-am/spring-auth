package br.com.alan.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alan.model.Role;
import br.com.alan.model.User;
import br.com.alan.repository.RoleRespository;
import br.com.alan.repository.UserRepository;
import jakarta.transaction.Transactional;

@Configuration
public class AdminInsertConfig implements CommandLineRunner {

    @Autowired
    private RoleRespository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findById(Role.Values.ADMIN.getRoleId());

        var userAdmin = userRepository.findByLogin("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setName("Administrador");
                    user.setEmail("admin@teste.com.br");
                    user.setLogin("admin");
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setRoles(Set.of(roleAdmin.get()));
                    userRepository.save(user);
                });
    }

}
