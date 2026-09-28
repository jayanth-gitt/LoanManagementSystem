package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger =
            LoggerFactory.getLogger(UserDaoImpl.class);
    private static final String statement =
            "INSERT INTO users (username, password, role, status) VALUES (?, ?, ?, ?)";
    private static final String statement1 = "SELECT * FROM users WHERE user_id = ?";
    private static final String statement2 = "UPDATE users SET username=?, password=?, role=?, status=? WHERE user_id=?";
    private static final String statement3 = "DELETE FROM users WHERE user_id=?";
    private static final  String statement4 = "SELECT * FROM users WHERE username = ?";
    private static final  String sql = "UPDATE users SET status=? WHERE user_id=?";

    @Override
    public void addUser(User user) {
        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps =
                     con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            if (user.getStatus() == null ||
                    user.getStatus().trim().isEmpty()) {

                user.setStatus("ACTIVE");
            }

            ps.setString(4, user.getStatus());

            ps.executeUpdate();

            // Get generated User ID
            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {

                    int generatedUserId = rs.getInt(1);

                    // Put generated ID back into User object
                    user.setUserId(generatedUserId);
                }
            }

        } catch (SQLException e) {

            logger.error("Error adding user", e);

            throw new RuntimeException("Unable to add user", e);
        }

    }
    @Override
    public User getUserById(int userId) {


        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement1);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(rs.getString("created_at"));

                return user;
            }

        } catch (Exception e) {
            logger.error("Error while getting user", e);
            throw new RuntimeException("Failed to get user", e);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {

        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement2);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getStatus());
            ps.setInt(5, user.getUserId());

            ps.executeUpdate();

            logger.info("User updated successfully");

        } catch (Exception e) {
            logger.error("Error while updating user", e);
            throw new RuntimeException("Failed to update user", e);
        }

    }

    @Override
    public void deleteUser(int userId) {




        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "INACTIVE");
            ps.setInt(2, userId);

            ps.executeUpdate();

            logger.info("User deactivated successfully");

        } catch (Exception e) {
            logger.error("Error while deactivating user", e);
            throw new RuntimeException("Failed to deactivate user", e);
        }
    }


    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(rs.getString("created_at"));

                users.add(user);
            }

            logger.info("All users retrieved successfully");

        } catch (Exception e) {

            logger.error("Error while retrieving users", e);
            throw new RuntimeException("Failed to retrieve users", e);
        }

        return users;
    }
    @Override
    public User getUserByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    User user = new User();

                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    user.setCreatedAt(rs.getString("created_at"));

                    return user;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error fetching user by username", e);
        }

        return null;
    }
}


