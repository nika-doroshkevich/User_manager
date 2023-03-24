package by.nika_doroshkevich.userManagement.service;

import org.springframework.security.core.Authentication;

public interface SpringContextService {
    Authentication getAuthentication();
}
