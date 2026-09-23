package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanApplicationDaoImpl implements LoanApplicationDao {
    @Override
    public void addLoanApplication(LoanApplication application) {
        String sql= """
                INSERT INTO loan_applications
                                (customer_id, loan_type_id, requested_amount,
                                 tenure_months, purpose, status, remarks,
                                 reviewed_by, applied_at, reviewed_at)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try(
                Connection connection= DBConnection.getConnection();
                PreparedStatement preparedStatement=connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, application.getCustomerId());
            preparedStatement.setInt(2, application.getLoanTypeId());
            preparedStatement.setDouble(3, application.getRequestedAmount());
            preparedStatement.setInt(4, application.getTenureMonths());
            preparedStatement.setString(5, application.getPurpose());
            preparedStatement.setString(6, application.getStatus());
            preparedStatement.setString(7, application.getRemarks());
            preparedStatement.setInt(8, application.getReviewedBy());
            preparedStatement.setString(9, application.getAppliedAt());
            preparedStatement.setString(10, application.getReviewedAt());

            preparedStatement.executeUpdate();

            System.out.println("Loan application added successfully.");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @Override
    public LoanApplication getLoanApplicationById(int applicationId) {

        String sql = """
                SELECT *
                FROM loan_applications
                WHERE application_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, applicationId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new LoanApplication(
                        resultSet.getInt("application_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("loan_type_id"),
                        resultSet.getDouble("requested_amount"),
                        resultSet.getInt("tenure_months"),
                        resultSet.getString("purpose"),
                        resultSet.getString("status"),
                        resultSet.getString("remarks"),
                        resultSet.getInt("reviewed_by"),
                        resultSet.getString("applied_at"),
                        resultSet.getString("reviewed_at")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateLoanApplication(LoanApplication application) {

        String sql = """
                UPDATE loan_applications
                SET
                    customer_id = ?,
                    loan_type_id = ?,
                    requested_amount = ?,
                    tenure_months = ?,
                    purpose = ?,
                    status = ?,
                    remarks = ?,
                    reviewed_by = ?,
                    applied_at = ?,
                    reviewed_at = ?
                WHERE application_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, application.getCustomerId());
            preparedStatement.setInt(2, application.getLoanTypeId());
            preparedStatement.setDouble(3, application.getRequestedAmount());
            preparedStatement.setInt(4, application.getTenureMonths());
            preparedStatement.setString(5, application.getPurpose());
            preparedStatement.setString(6, application.getStatus());
            preparedStatement.setString(7, application.getRemarks());
            preparedStatement.setInt(8, application.getReviewedBy());
            preparedStatement.setString(9, application.getAppliedAt());
            preparedStatement.setString(10, application.getReviewedAt());
            preparedStatement.setInt(11, application.getApplicationId());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(
                    rowsAffected + " loan application(s) updated."
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoanApplication(int applicationId) {

        String sql = """
                DELETE FROM loan_applications
                WHERE application_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, applicationId);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(
                    rowsAffected + " loan application(s) deleted."
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
