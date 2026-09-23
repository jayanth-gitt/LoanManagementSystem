package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanDaoImpl implements LoanDao {

    @Override
    public void addLoan(Loan loan) {

        String sql = """
                INSERT INTO loans
                (application_id, customer_id, loan_type_id,
                 principal_amount, interest_rate, tenure_months,
                 emi_amount, start_date, end_date,
                 outstanding_amount, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
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

            preparedStatement.executeUpdate();

            System.out.println("Loan added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Loan getLoanById(int loanId) {

        String sql = """
                SELECT *
                FROM loans
                WHERE loan_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, loanId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new Loan(
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateLoan(Loan loan) {

        String sql = """
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

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
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

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " loan(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoan(int loanId) {

        String sql = """
                DELETE FROM loans
                WHERE loan_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, loanId);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " loan(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}