package com.manager.usrmanagertask.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Boolean wasDeleted = (Boolean) servletRequest.getAttribute("wasBlocked");

        if (wasDeleted == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (wasDeleted) {
            RequestDispatcher dispatcher = servletRequest.getServletContext().getRequestDispatcher("/logout");
            dispatcher.forward(servletRequest, servletResponse);
            return;
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
