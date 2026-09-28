package com.loanmanagement.service;

import com.loanmanagement.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    User getUserById(int userId);
    User getUserByUsername(String username);

    void updateUser(User user);

    void deleteUser(int userId);

    List<User> getAllUsers();

}
