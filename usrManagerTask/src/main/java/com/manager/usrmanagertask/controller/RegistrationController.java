package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.enums.Role;
import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.repository.UserRepository;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        if (user.getUsername().equals("anonymousUser")) {
            model.put("message", "User exists!");
            return "registration";
        }

        User userFromDb = userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Collections.singleton(Role.USER));
        user.setBlocked(false);
        user.setDeleted(false);

        //Set registration date
        LocalDate now = LocalDate.now();
        user.setRegistrationDate(now);

        userRepository.save(user);
        return "redirect:/login";
    }
}
