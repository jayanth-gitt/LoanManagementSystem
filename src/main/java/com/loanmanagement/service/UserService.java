package com.loanmanagement.service;

import com.loanmanagement.model.User;

public interface UserService {
    int addUser(User user);

    User getUserById(int userId);

    int updateUser(User user);

    int deleteUser(int userId);
}
