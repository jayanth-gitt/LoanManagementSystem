package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanTypeDaoImpl implements LoanTypeDao {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanTypeDaoImpl.class);

    public static final String INSERT_LOAN_TYPE_SQL = """
            INSERT INTO loan_types
            (name, description, min_amount, max_amount,
             interest_rate, max_tenure_months, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String SELECT_LOAN_TYPE_BY_ID_SQL = """
            SELECT *
            FROM loan_types
            WHERE loan_type_id = ?
            """;

    public static final String UPDATE_LOAN_TYPE_SQL = """
            UPDATE loan_types
            SET
                name = ?,
                description = ?,
                min_amount = ?,
                max_amount = ?,
                interest_rate = ?,
                max_tenure_months = ?,
                status = ?
            WHERE loan_type_id = ?
            """;

    public static final String DELETE_LOAN_TYPE_SQL = """
            DELETE FROM loan_types
            WHERE loan_type_id = ?
            """;

    @Override
    public int addLoanType(LoanType loanType) {

        logger.info(
                "Adding loan type: {}",
                loanType.getName()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_LOAN_TYPE_SQL)
        ) {

            preparedStatement.setString(
                    1,
                    loanType.getName()
            );

            preparedStatement.setString(
                    2,
                    loanType.getDescription()
            );

            preparedStatement.setDouble(
                    3,
                    loanType.getMinAmount()
            );

            preparedStatement.setDouble(
                    4,
                    loanType.getMaxAmount()
            );

            preparedStatement.setDouble(
                    5,
                    loanType.getInterestRate()
            );

            preparedStatement.setInt(
                    6,
                    loanType.getMaxTenureMonths()
            );

            preparedStatement.setString(
                    7,
                    loanType.getStatus()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            logger.info(
                    "Loan type '{}' added successfully. Rows affected: {}",
                    loanType.getName(),
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while adding loan type: {}",
                    loanType.getName(),
                    e
            );

            return 0;
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {

        logger.info(
                "Fetching loan type with ID: {}",
                loanTypeId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                SELECT_LOAN_TYPE_BY_ID_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    loanTypeId
            );

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    LoanType loanType =
                            new LoanType(
                                    resultSet.getInt("loan_type_id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("description"),
                                    resultSet.getDouble("min_amount"),
                                    resultSet.getDouble("max_amount"),
                                    resultSet.getDouble("interest_rate"),
                                    resultSet.getInt("max_tenure_months"),
                                    resultSet.getString("status")
                            );

                    logger.info(
                            "Loan type found with ID: {}",
                            loanTypeId
                    );

                    return loanType;
                }
            }

            logger.warn(
                    "No loan type found with ID: {}",
                    loanTypeId
            );

        } catch (SQLException e) {

            logger.error(
                    "Error while fetching loan type with ID: {}",
                    loanTypeId,
                    e
            );
        }

        return null;
    }

    @Override
    public int updateLoanType(LoanType loanType) {

        logger.info(
                "Updating loan type with ID: {}",
                loanType.getLoanTypeId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                UPDATE_LOAN_TYPE_SQL
                        )
        ) {

            preparedStatement.setString(
                    1,
                    loanType.getName()
            );

            preparedStatement.setString(
                    2,
                    loanType.getDescription()
            );

            preparedStatement.setDouble(
                    3,
                    loanType.getMinAmount()
            );

            preparedStatement.setDouble(
                    4,
                    loanType.getMaxAmount()
            );

            preparedStatement.setDouble(
                    5,
                    loanType.getInterestRate()
            );

            preparedStatement.setInt(
                    6,
                    loanType.getMaxTenureMonths()
            );

            preparedStatement.setString(
                    7,
                    loanType.getStatus()
            );

            preparedStatement.setInt(
                    8,
                    loanType.getLoanTypeId()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan type with ID {} updated successfully. Rows affected: {}",
                        loanType.getLoanTypeId(),
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan type found to update with ID: {}",
                        loanType.getLoanTypeId()
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while updating loan type with ID: {}",
                    loanType.getLoanTypeId(),
                    e
            );

            return 0;
        }
    }

    @Override
    public int deleteLoanType(int loanTypeId) {

        logger.info(
                "Deleting loan type with ID: {}",
                loanTypeId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                DELETE_LOAN_TYPE_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    loanTypeId
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan type with ID {} deleted successfully. Rows affected: {}",
                        loanTypeId,
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan type found to delete with ID: {}",
                        loanTypeId
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting loan type with ID: {}",
                    loanTypeId,
                    e
            );

            return 0;
        }
    }
}