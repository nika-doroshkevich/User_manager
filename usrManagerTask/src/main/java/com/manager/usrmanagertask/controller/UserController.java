package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        List<User> users = userService.findByDeleted(false);
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findById(id);
        if (user.getUsername().equals(auth.getName())){
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        user.setDeleted(true);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("user-block/{id}")
    public String blockUser(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findById(id);
        if (user.getUsername().equals(auth.getName())){
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        user.setBlocked(true);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("user-unblock/{id}")
    public String unblockUser(@PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findById(id);
        if (user.getUsername().equals(auth.getName())){
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        user.setBlocked(false);
        userService.saveUser(user);
        return "redirect:/";
    }
}
