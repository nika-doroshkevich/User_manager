package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.dto.UserDto;
import com.manager.usrmanagertask.enums.UserStatus;
import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

import static com.manager.usrmanagertask.enums.Role.USER;

@Controller
@RequestMapping(value = "/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private static final String REGISTRATION_VIEW = "registration";

    private static final String DEFAULT_SPRING_USER = "anonymousUser";
    private static final String USER_EXISTS_ERROR = "User exists!";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userDto", new UserDto());
        return REGISTRATION_VIEW;
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return REGISTRATION_VIEW;
        }

        if (validateUserDto(userDto, model)) {
            return REGISTRATION_VIEW;
        }

        userService.saveUser(createUser(userDto));

        return "redirect:/login";
    }

    private boolean validateUserDto(UserDto userDto, Model model) {
        if (userDto.getUsername().equals(DEFAULT_SPRING_USER)
                || userService.getUserByUsername(userDto.getUsername()) != null) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", USER_EXISTS_ERROR);
            return true;
        }
        return false;
    }

    private User createUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .registrationDate(LocalDate.now())
                .status(UserStatus.ACTIVE)
                .roles(Set.of(USER))
                .build();
    }
}
