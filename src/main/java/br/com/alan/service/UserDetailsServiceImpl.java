package br.com.alan.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alan.model.Role;
import br.com.alan.model.User;
import br.com.alan.model.UserDetailsImpl;
import br.com.alan.repository.RoleRespository;
import br.com.alan.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RoleRespository roleRespository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username).get();

                List<Role> roles = roleRespository.findByUserRoles(user.getId());
                user.setRoles(roles);

                UserDetails userDetails = new UserDetailsImpl(user);
                return userDetails;
        }
}
