package com.loanmanagement;

import com.loanmanagement.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class AuthServiceImplTest {
    @Test
    void loginWithValidCredentials() {

        AuthServiceImpl authService = new AuthServiceImpl();

        boolean result = authService.login("deepthi", "1994");

        assertTrue(result);
    }
    @Test
    void loginWithWrongPassword() {

        AuthServiceImpl authService = new AuthServiceImpl();

        boolean result = authService.login("deepthi", "wrongPassword");

        assertFalse(result);
    }
    @Test
    void loginWithInactiveUser() {

        AuthServiceImpl authService = new AuthServiceImpl();

        boolean result = authService.login("inactive_test_user", "PASSWORD");

        assertFalse(result);
    }
}
