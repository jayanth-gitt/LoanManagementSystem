package com.loanmanagement.dao;

import com.loanmanagement.model.User;

public interface UserDao {


        int addUser(User user);

        User getUserById(int userId);

        int updateUser(User user);

        int deleteUser(int userId);

}
