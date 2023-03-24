package com.manager.usrmanagertask.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String REDIRECT_URL = "/login?error=";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthException authException = AuthException.valueOfSpringException(exception.getMessage());
        String fullRedirectUrl = REDIRECT_URL + authException.getCode();

        super.setDefaultFailureUrl(fullRedirectUrl);
        super.onAuthenticationFailure(request, response, exception);
    }
}

