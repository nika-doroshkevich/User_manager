package by.nika_doroshkevich.userManagement.controller;

import by.nika_doroshkevich.userManagement.dto.UserDto;
import by.nika_doroshkevich.userManagement.enums.UserStatus;
import by.nika_doroshkevich.userManagement.model.User;
import by.nika_doroshkevich.userManagement.service.UserService;
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

import static by.nika_doroshkevich.userManagement.enums.Role.USER;

@Controller
@RequestMapping(value = "/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private static final String REGISTRATION_VIEW = "registration";

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

        userService.saveUser(createUser(userDto));

        return "redirect:/login";
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
