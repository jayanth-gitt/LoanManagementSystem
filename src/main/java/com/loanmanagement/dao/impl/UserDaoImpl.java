package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final Logger logger =
            LoggerFactory.getLogger(UserDaoImpl.class);

    public static final String INSERT_USER_SQL = """
            INSERT INTO users
            (username, password_hash, role, status)
            VALUES (?, ?, ?, ?)
            """;

    public static final String SELECT_USER_BY_ID_SQL = """
            SELECT *
            FROM users
            WHERE user_id = ?
            """;

    public static final String UPDATE_USER_SQL = """
            UPDATE users
            SET
                username = ?,
                password_hash = ?,
                role = ?,
                status = ?
            WHERE user_id = ?
            """;

    public static final String DELETE_USER_SQL = """
            DELETE FROM users
            WHERE user_id = ?
            """;

    @Override
    public int addUser(User user) {

        logger.info("Adding user: {}", user.getUsername());

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_USER_SQL)
        ) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info(
                    "User '{}' added successfully. Rows affected: {}",
                    user.getUsername(),
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while adding user: {}",
                    user.getUsername(),
                    e
            );

            return 0;
        }
    }

    @Override
    public User getUserById(int userId) {

        logger.info("Fetching user with ID: {}", userId);

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SELECT_USER_BY_ID_SQL)
        ) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password_hash"),
                            resultSet.getString("role"),
                            resultSet.getString("status"),
                            resultSet.getString("created_at")
                    );

                    logger.info(
                            "User found with ID: {}",
                            userId
                    );

                    return user;
                }
            }

            logger.warn(
                    "No user found with ID: {}",
                    userId
            );

        } catch (SQLException e) {

            logger.error(
                    "Error while fetching user with ID: {}",
                    userId,
                    e
            );
        }

        return null;
    }

    @Override
    public int updateUser(User user) {

        logger.info(
                "Updating user with ID: {}",
                user.getUserId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(UPDATE_USER_SQL)
        ) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getStatus());
            preparedStatement.setInt(5, user.getUserId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "User with ID {} updated successfully. Rows affected: {}",
                        user.getUserId(),
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No user found to update with ID: {}",
                        user.getUserId()
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while updating user with ID: {}",
                    user.getUserId(),
                    e
            );

            return 0;
        }
    }

    @Override
    public int deleteUser(int userId) {

        logger.info(
                "Deleting user with ID: {}",
                userId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_USER_SQL)
        ) {

            preparedStatement.setInt(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "User with ID {} deleted successfully. Rows affected: {}",
                        userId,
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No user found to delete with ID: {}",
                        userId
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting user with ID: {}",
                    userId,
                    e
            );

            return 0;
        }
    }
}