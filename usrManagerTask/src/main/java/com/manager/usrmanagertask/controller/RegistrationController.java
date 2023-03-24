package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.enums.Role;
import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.repository.UserRepository;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (user.getUsername().equals("anonymousUser")) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", "User exists!");
            return "registration";
        }

        User userFromDb = userService.loadUserByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", "User exists!");
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

    @GetMapping("/login")
    public ModelAndView login(final HttpServletRequest request,
                              final ModelMap model,
                              @RequestParam("messageKey" ) final Optional<String> messageKey,
                              @RequestParam("error" ) final Optional<String> error) {

        model.addAttribute("error", "true");
        return new ModelAndView("login", model);
    }
}
