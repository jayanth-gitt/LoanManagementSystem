package com.loanmanagement;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;
import org.junit.jupiter.api.Test;

public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();
    @Test
    public void testAddUser() {

        User user = new User();

        user.setUsername("deepthi");
        user.setPassword("1994");
        user.setRole("CUSTOMER");
        user.setStatus("ACTIVE");

        userDao.addUser(user);
    }
    @Test
    public void testGetUserById() {

        User user = userDao.getUserById(17);

        System.out.println("User ID: " + user.getUserId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Role: " + user.getRole());
    }
    @Test
    public void testUpdateUser() {

        User user = userDao.getUserById(17);

        user.setPassword("newpassword");
        user.setStatus("ACTIVE");

        userDao.updateUser(user);
    }
    @Test
    public void testDeleteUser() {

        userDao.deleteUser(18);
    }
}
