package org.example;
import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;

public class Main {

    public static void main(String[] args) {


        UserDao userDao = new UserDaoImpl();

        userDao.deleteUser(1);
    }
}