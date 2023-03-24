package com.manager.usrmanagertask.filters;

import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteAndBlockUserFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpRequest = request;
        HttpServletResponse httpResponse = response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        List<String> whiteList = new ArrayList<>();
        whiteList.add("/login");
        whiteList.add("/resources/css/style.css");
        whiteList.add("/favicon.ico");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String principalName = auth.getName();

        if (principalName.equals("anonymousUser")) {
            whiteList.add("/registration");
        }

        if (whiteList.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            if (principalName.equals("anonymousUser")) {
                filterChain.doFilter(request, response);
                return;
            } else {
                User user = userService.loadUserByUsername(principalName);
                if (user == null || user.getDeleted() || user.getBlocked()) {
                    httpResponse.sendRedirect("/logout");
                } else {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
    }
}
