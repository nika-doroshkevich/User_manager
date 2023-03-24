package by.nika_doroshkevich.userManagement.service;

import by.nika_doroshkevich.userManagement.enums.UserStatus;
import by.nika_doroshkevich.userManagement.model.User;
import by.nika_doroshkevich.userManagement.security.AuthException;
import by.nika_doroshkevich.userManagement.repository.UserRepository;
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

        User user = userService.getUserByUsername(username, UserStatus.ACTIVE);

        if (user == null) {
            throw new UsernameNotFoundException(AuthException.NOT_FOUND.getAppException());
        }

        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            throw new IllegalStateException(AuthException.IS_BLOCKED.getAppException());
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
