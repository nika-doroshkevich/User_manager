package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.repository.UserRepository;
import com.manager.usrmanagertask.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currUser = userService.findById(id);
        if (currUser.getUsername().equals(auth.getName())){
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        userService.deleteById(id);
        return "redirect:/";
    }
}
