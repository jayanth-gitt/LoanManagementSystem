package com.loanmanagement.service.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;
import com.loanmanagement.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthServiceImpl implements AuthService {
    private static final Logger logger =
            LoggerFactory.getLogger(AuthServiceImpl.class);
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean login(String username, String password) {

        User user = userDao.getUserByUsername(username);

        if (user == null) {
            logger.warn("Login failed: user not found");
            return false;
        }

        if (!user.getStatus().equals("ACTIVE")) {
            logger.warn("Login failed: user is inactive");
            return false;
        }
        if (user.getPassword().equals(password)) {
            logger.info("User login successful");
            return true;
        }
        logger.warn("Login failed: invalid credentials");
        return false;


    }

    @Override
    public void logout(int userId) {
        logger.info("User logged out successfully");
    }
}
