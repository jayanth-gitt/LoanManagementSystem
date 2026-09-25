package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanDaoImpl implements LoanDao {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanDaoImpl.class);

    public static final String INSERT_LOAN_SQL = """
            INSERT INTO loans
            (application_id, customer_id, loan_type_id,
             principal_amount, interest_rate, tenure_months,
             emi_amount, start_date, end_date,
             outstanding_amount, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String SELECT_LOAN_BY_ID_SQL = """
            SELECT *
            FROM loans
            WHERE loan_id = ?
            """;

    public static final String UPDATE_LOAN_SQL = """
            UPDATE loans
            SET
                application_id = ?,
                customer_id = ?,
                loan_type_id = ?,
                principal_amount = ?,
                interest_rate = ?,
                tenure_months = ?,
                emi_amount = ?,
                start_date = ?,
                end_date = ?,
                outstanding_amount = ?,
                status = ?
            WHERE loan_id = ?
            """;

    public static final String DELETE_LOAN_SQL = """
            DELETE FROM loans
            WHERE loan_id = ?
            """;

    @Override
    public int addLoan(Loan loan) {

        logger.info(
                "Adding loan for application ID: {}",
                loan.getApplicationId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_LOAN_SQL)
        ) {

            preparedStatement.setInt(1, loan.getApplicationId());
            preparedStatement.setInt(2, loan.getCustomerId());
            preparedStatement.setInt(3, loan.getLoanTypeId());
            preparedStatement.setDouble(4, loan.getPrincipalAmount());
            preparedStatement.setDouble(5, loan.getInterestRate());
            preparedStatement.setInt(6, loan.getTenureMonths());
            preparedStatement.setDouble(7, loan.getEmiAmount());
            preparedStatement.setString(8, loan.getStartDate());
            preparedStatement.setString(9, loan.getEndDate());
            preparedStatement.setDouble(10, loan.getOutstandingAmount());
            preparedStatement.setString(11, loan.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info(
                    "Loan added successfully. Rows affected: {}",
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while adding loan for application ID: {}",
                    loan.getApplicationId(),
                    e
            );

            return 0;
        }
    }

    @Override
    public Loan getLoanById(int loanId) {

        logger.info(
                "Fetching loan with ID: {}",
                loanId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(SELECT_LOAN_BY_ID_SQL)
        ) {

            preparedStatement.setInt(1, loanId);

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    Loan loan = new Loan(
                            resultSet.getInt("loan_id"),
                            resultSet.getInt("application_id"),
                            resultSet.getInt("customer_id"),
                            resultSet.getInt("loan_type_id"),
                            resultSet.getDouble("principal_amount"),
                            resultSet.getDouble("interest_rate"),
                            resultSet.getInt("tenure_months"),
                            resultSet.getDouble("emi_amount"),
                            resultSet.getString("start_date"),
                            resultSet.getString("end_date"),
                            resultSet.getDouble("outstanding_amount"),
                            resultSet.getString("status")
                    );

                    logger.info(
                            "Loan found with ID: {}",
                            loanId
                    );

                    return loan;
                }
            }

            logger.warn(
                    "No loan found with ID: {}",
                    loanId
            );

        } catch (SQLException e) {

            logger.error(
                    "Error while fetching loan with ID: {}",
                    loanId,
                    e
            );
        }

        return null;
    }

    @Override
    public int updateLoan(Loan loan) {

        logger.info(
                "Updating loan with ID: {}",
                loan.getLoanId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(UPDATE_LOAN_SQL)
        ) {

            preparedStatement.setInt(1, loan.getApplicationId());
            preparedStatement.setInt(2, loan.getCustomerId());
            preparedStatement.setInt(3, loan.getLoanTypeId());
            preparedStatement.setDouble(4, loan.getPrincipalAmount());
            preparedStatement.setDouble(5, loan.getInterestRate());
            preparedStatement.setInt(6, loan.getTenureMonths());
            preparedStatement.setDouble(7, loan.getEmiAmount());
            preparedStatement.setString(8, loan.getStartDate());
            preparedStatement.setString(9, loan.getEndDate());
            preparedStatement.setDouble(10, loan.getOutstandingAmount());
            preparedStatement.setString(11, loan.getStatus());
            preparedStatement.setInt(12, loan.getLoanId());

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan with ID {} updated successfully. Rows affected: {}",
                        loan.getLoanId(),
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan found to update with ID: {}",
                        loan.getLoanId()
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while updating loan with ID: {}",
                    loan.getLoanId(),
                    e
            );

            return 0;
        }
    }

    @Override
    public int deleteLoan(int loanId) {

        logger.info(
                "Deleting loan with ID: {}",
                loanId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_LOAN_SQL)
        ) {

            preparedStatement.setInt(1, loanId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan with ID {} deleted successfully. Rows affected: {}",
                        loanId,
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan found to delete with ID: {}",
                        loanId
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting loan with ID: {}",
                    loanId,
                    e
            );

            return 0;
        }
    }
}