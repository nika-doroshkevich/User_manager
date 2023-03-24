package com.manager.usrmanagertask.service;

import com.manager.usrmanagertask.enums.Role;
import com.manager.usrmanagertask.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.manager.usrmanagertask.model.User user = userRepository.findByUsername(username);

        if (user.getBlocked()) {
            throw new IllegalStateException("User is blocked!");
        }

        UserDetails ud =
                User.withDefaultPasswordEncoder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRoles().stream().map(Role::name).toArray(String[]::new))
                        .build();

        LocalDate now = LocalDate.now();
        user.setLastLoginDate(now);
        userRepository.save(user);

        return ud;
    }
}
