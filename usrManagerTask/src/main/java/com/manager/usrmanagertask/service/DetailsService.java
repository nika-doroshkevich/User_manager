package com.manager.usrmanagertask.service;

import com.manager.usrmanagertask.enums.Role;
import com.manager.usrmanagertask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.manager.usrmanagertask.model.User user = userRepository.findByUsername(username);

        UserDetails ud =
                User.withDefaultPasswordEncoder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(Role::name).toArray(String[]::new))
                        .build();

        return ud;
    }
}
