package com.loanmanagement.service.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;
import com.loanmanagement.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(int userId) {
        userDao.deleteUser(userId);
    }
}