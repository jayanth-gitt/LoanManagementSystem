package com.loanmanagement.controller;

import com.loanmanagement.service.AuthService;
import com.loanmanagement.service.impl.AuthServiceImpl;

public class AuthController {
    private final AuthService authService =
            new AuthServiceImpl();

    public boolean login(String username, String password) {
        return authService.login(username, password);
    }

    public void logout(int userId) {
        authService.logout(userId);
    }
}
