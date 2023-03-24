package com.manager.usrmanagertask.service;

import static com.manager.usrmanagertask.security.AuthException.IS_BLOCKED;
import static com.manager.usrmanagertask.security.AuthException.NOT_FOUND;

import com.manager.usrmanagertask.enums.UserStatus;
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

        com.manager.usrmanagertask.model.User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(NOT_FOUND.getAppException());
        }

        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new IllegalStateException(IS_BLOCKED.getAppException());
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
