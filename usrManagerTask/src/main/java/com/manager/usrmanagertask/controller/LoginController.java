package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.security.AuthException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error, ModelMap model) {
        if (error != null) {
            AuthException authException = AuthException.valueOfCode(error);
            model.addAttribute("error", authException.getAppException());
        }

        return new ModelAndView("login", model);
    }
}

