package com.manager.usrmanagertask.controller;

import com.manager.usrmanagertask.dto.EditUsersDto;
import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.manager.usrmanagertask.enums.UserStatus.ACTIVE;
import static com.manager.usrmanagertask.enums.UserStatus.BLOCKED;
import static com.manager.usrmanagertask.enums.UserStatus.DELETED;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String greeting(Model model) {
        List<User> users = userService.findByStatusIsNot(DELETED);
        model.addAttribute("users", users);
        model.addAttribute("editUsersDto", new EditUsersDto());
        return "user-list";
    }

    @PostMapping(value="/user/edit", params="action=block")
    public ModelAndView block(@ModelAttribute("editUsersDto") EditUsersDto editUsersDto, ModelMap model) {
        List<Integer> userIds = editUsersDto.getUserIds();
        userService.updateStatusForUsers(BLOCKED, userIds);
        return new ModelAndView("redirect:/", model);
    }

    @PostMapping(value="/user/edit", params="action=unblock")
    public ModelAndView unblock(@ModelAttribute("editUsersDto") EditUsersDto editUsersDto, ModelMap model) {
        List<Integer> userIds = editUsersDto.getUserIds();
        userService.updateStatusForUsers(ACTIVE, userIds);
        return new ModelAndView("redirect:/", model);
    }

    @PostMapping(value="/user/edit", params="action=delete")
    public ModelAndView delete(@ModelAttribute("editUsersDto") EditUsersDto editUsersDto, ModelMap model) {
        List<Integer> userIds = editUsersDto.getUserIds();
        userService.deleteUsers(userIds);
        return new ModelAndView("redirect:/", model);
    }
}
