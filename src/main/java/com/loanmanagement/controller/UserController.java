package com.loanmanagement.controller;

import com.loanmanagement.model.User;
import com.loanmanagement.service.UserService;
import com.loanmanagement.service.impl.UserServiceImpl;

import java.util.List;

public class UserController {
    private final UserService userService =
            new UserServiceImpl();

    public void addUser(User user) {
        userService.addUser(user);
    }

    public User getUserById(int userId) {
        return userService.getUserById(userId);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }



    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
}
