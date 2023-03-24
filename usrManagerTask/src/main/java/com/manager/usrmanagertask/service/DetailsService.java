package com.manager.usrmanagertask.service;

import com.manager.usrmanagertask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();

        com.manager.usrmanagertask.model.User user = userService.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        if (user.getBlocked()) {
            throw new IllegalStateException("User is blocked!");
        }

        LocalDate now = LocalDate.now();
        user.setLastLoginDate(now);
        userRepository.save(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
