package by.nika_doroshkevich.userManagement.controller;

import by.nika_doroshkevich.userManagement.dto.EditUsersDto;
import by.nika_doroshkevich.userManagement.model.User;
import by.nika_doroshkevich.userManagement.service.SpringContextService;
import by.nika_doroshkevich.userManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static by.nika_doroshkevich.userManagement.enums.UserStatus.ACTIVE;
import static by.nika_doroshkevich.userManagement.enums.UserStatus.BLOCKED;
import static by.nika_doroshkevich.userManagement.enums.UserStatus.DELETED;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SpringContextService springContextService;

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

        String authName = springContextService.getAuthentication().getName();
        User user = userService.getUserByUsernameAndStatusIsNot(authName, DELETED);
        if (user.getStatus().equals(BLOCKED)) {
            return new ModelAndView("redirect:/logout", model);
        }

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

        String authName = springContextService.getAuthentication().getName();
        User user = userService.getUserByUsernameAndStatusIsNot(authName, DELETED);
        if (user.getStatus().equals(BLOCKED)) {
            return new ModelAndView("redirect:/logout", model);
        }

        return new ModelAndView("redirect:/", model);
    }
}
