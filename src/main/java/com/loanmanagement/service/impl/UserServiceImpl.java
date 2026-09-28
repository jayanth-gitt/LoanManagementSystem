package com.loanmanagement.service.impl;

import com.loanmanagement.controller.AppController;
import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.exception.ValidationException;
import com.loanmanagement.model.User;
import com.loanmanagement.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void addUser(User user) {
        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (user.getRole() == null ||
                user.getRole().trim().isEmpty()) {
            throw new ValidationException("User role is required");
        }

        if (!"ADMIN".equals(user.getRole())
                && !"LOAN_OFFICER".equals(user.getRole())
                && !"CUSTOMER".equals(user.getRole())) {
            throw new ValidationException("Invalid user role");
        }

        if (user.getStatus() == null ||
                user.getStatus().trim().isEmpty()) {
            user.setStatus("ACTIVE");
        }
        userDao.addUser(user);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (user.getRole() == null ||
                user.getRole().trim().isEmpty()) {
            throw new ValidationException("User role is required");
        }

        if (!"ADMIN".equals(user.getRole())
                && !"LOAN_OFFICER".equals(user.getRole())
                && !"CUSTOMER".equals(user.getRole())) {
            throw new ValidationException("Invalid user role");
        }
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        userDao.deleteUser( userId);
    }
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}