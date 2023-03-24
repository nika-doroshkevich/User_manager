package com.manager.usrmanagertask.security;

import java.util.HashMap;
import java.util.Map;

public enum AuthException {

    BAD_CREDENTIALS(1, "Bad credentials", "Invalid username or password"),
    IS_BLOCKED(2, "User is blocked!", "User is blocked!"),
    NOT_FOUND(3, "User not found!", "User not found!"),
    DEFAULT(4, "Invalid username or password", "Invalid username or password");

    private static final Map<Integer, AuthException> BY_code = new HashMap<>();
    private static final Map<String, AuthException> BY_SPRING_EXCEPTION = new HashMap<>();

    static {
        for (AuthException e : values()) {
            BY_code.put(e.code, e);
            BY_SPRING_EXCEPTION.put(e.springException, e);
        }
    }

    private final int code;
    private final String springException;
    private final String appException;

    AuthException(int code, String springException, String appException) {
        this.code = code;
        this.springException = springException;
        this.appException = appException;
    }

    public int getCode() {
        return code;
    }

    public String getSpringException() {
        return springException;
    }

    public String getAppException() {
        return appException;
    }

    public static AuthException valueOfCode(Integer code) {
        AuthException authException = BY_code.get(code);
        if (authException == null) {
            return AuthException.DEFAULT;
        }
        return authException;
    }

    public static AuthException valueOfCode(String code) {
        return valueOfCode(Integer.parseInt(code));
    }

    public static AuthException valueOfSpringException(String springException) {
        AuthException authException = BY_SPRING_EXCEPTION.get(springException);
        if (authException == null) {
            return AuthException.DEFAULT;
        }
        return authException;
    }
}

