package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public void addUser(User user) {
        String sql= """
                insert into users
                (username, password_hash, role, status, created_at)
                values(?,?,?,?,?)
                """;
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());
            preparedStatement.setString(5, user.getCreatedAt());
            preparedStatement.executeUpdate();

            System.out.println("User added successfully.");

    }catch(SQLException e){
        e.printStackTrace();
    }
}

    @Override
    public User getUserById(int userId) {
        String sql = """
                select user_id,username,password_hash,role,status,created_at
                from users
                where user_id=?
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("role"),
                        resultSet.getString("status"),
                        resultSet.getString("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void updateUser(User user) {
        String sql= """
                update users set username=?,
                                 password_hash=?,
                                 role=?,
                                 status=?,
                                 created_at=?
                        where user_id=?
                """;
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2,user.getPasswordHash());
            preparedStatement.setString(3,user.getRole());
            preparedStatement.setString(4,user.getStatus());
            preparedStatement.setString(5,user.getCreatedAt());

            preparedStatement.setInt(6,user.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected+ " user(s) updated.");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(int userId) {
        String sql= """
                delete from users where user_id =?
                """;
        try(Connection connection=DBConnection.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, userId);

            int rowsAffected=preparedStatement.executeUpdate();
            System.out.println(rowsAffected+" user(s) deleted.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
