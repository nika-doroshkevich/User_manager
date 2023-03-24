package com.manager.usrmanagertask.filters;

import com.manager.usrmanagertask.model.User;
import com.manager.usrmanagertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        List<String> whiteList = new ArrayList<>();
        whiteList.add("/login");
        whiteList.add("/resources/css/style.css");
        whiteList.add("/favicon.ico");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalName = auth.getName();

        if (principalName.equals("anonymousUser")) {
            whiteList.add("/registration");
        }

        if (whiteList.contains(path)) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            if (principalName.equals("anonymousUser")) {
                filterChain.doFilter(httpRequest, httpResponse);
            } else {
                User user = userService.loadUserByUsername(principalName);
                if (user == null || user.getDeleted() || user.getBlocked()) {
                    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                    httpResponse.sendRedirect("/login");
                    filterChain.doFilter(httpRequest, httpResponse);
                } else {
                    filterChain.doFilter(httpRequest, httpResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
