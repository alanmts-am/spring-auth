package br.com.alan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.alan.model.User;
import br.com.alan.model.UserAuthenticated;
import br.com.alan.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = this.userRepository.findByUsername(username);

        return user.map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
