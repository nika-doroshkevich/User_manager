package com.manager.usrmanagertask.repository;

import com.manager.usrmanagertask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByUsernameAndDeleted(String username, Boolean deleted);
    List<User> findByDeleted(Boolean deleted);
}
