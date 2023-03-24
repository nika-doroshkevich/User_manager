package com.manager.usrmanagertask.service;

import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> findByDeleted(Boolean deleted) {
        return userRepository.findByDeleted(deleted);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        List<User> users = userRepository.findByUsernameAndDeleted(username, false);
        if (users.size() > 1) {
            throw new IllegalStateException("More than one username with current username");
        }

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }
}
