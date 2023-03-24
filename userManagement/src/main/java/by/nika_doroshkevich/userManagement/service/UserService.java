package by.nika_doroshkevich.userManagement.service;

import by.nika_doroshkevich.userManagement.enums.UserStatus;
import by.nika_doroshkevich.userManagement.model.User;
import by.nika_doroshkevich.userManagement.repository.UserRepository;
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

    public User getUserByUsernameAndStatusIsNot(String username, UserStatus userStatus) {
        List<User> users = userRepository.findByUsernameAndStatusIsNot(username, userStatus);

        if (users.size() > 1) {
            throw new IllegalStateException("More than one username with current username!");
        }

        if (users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    public User getUserByUsername(String username, UserStatus userStatus) throws UsernameNotFoundException {

        List<User> users = userRepository.findByUsernameAndStatus(username, userStatus);
        if (users.size() > 1) {
            throw new IllegalStateException("More than one username with current username!");
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
