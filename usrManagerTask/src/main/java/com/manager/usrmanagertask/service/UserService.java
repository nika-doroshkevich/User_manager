package com.manager.usrmanagertask.service;

import com.manager.usrmanagertask.enums.UserStatus;
import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findByStatusIsNot(UserStatus userStatus) {
        return userRepository.findByStatusIsNot(userStatus);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {

        List<User> users = userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
        if (users.size() > 1) {
            throw new IllegalStateException("More than one username with current username");
        }

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    @Transactional
    public void updateStatusForUsers(UserStatus userStatus, List<Integer> ids) {
        userRepository.updateStatusForUsers(userStatus, ids);
    }

    @Transactional
    public void deleteUsers(List<Integer> ids) {
        userRepository.deleteUsers(ids);
    }
}
