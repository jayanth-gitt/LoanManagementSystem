package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanTypeDaoImpl implements LoanTypeDao {

    @Override
    public void addLoanType(LoanType loanType) {

        String sql = """
                INSERT INTO loan_types
                (name, description, min_amount, max_amount,
                 interest_rate, max_tenure_months, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, loanType.getName());
            preparedStatement.setString(2, loanType.getDescription());
            preparedStatement.setDouble(3, loanType.getMinAmount());
            preparedStatement.setDouble(4, loanType.getMaxAmount());
            preparedStatement.setDouble(5, loanType.getInterestRate());
            preparedStatement.setInt(6, loanType.getMaxTenureMonths());
            preparedStatement.setString(7, loanType.getStatus());

            preparedStatement.executeUpdate();

            System.out.println("Loan type added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {

        String sql = """
                SELECT *
                FROM loan_types
                WHERE loan_type_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, loanTypeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new LoanType(
                        resultSet.getInt("loan_type_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("min_amount"),
                        resultSet.getDouble("max_amount"),
                        resultSet.getDouble("interest_rate"),
                        resultSet.getInt("max_tenure_months"),
                        resultSet.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {

        String sql = """
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

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, loanType.getName());
            preparedStatement.setString(2, loanType.getDescription());
            preparedStatement.setDouble(3, loanType.getMinAmount());
            preparedStatement.setDouble(4, loanType.getMaxAmount());
            preparedStatement.setDouble(5, loanType.getInterestRate());
            preparedStatement.setInt(6, loanType.getMaxTenureMonths());
            preparedStatement.setString(7, loanType.getStatus());
            preparedStatement.setInt(8, loanType.getLoanTypeId());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " loan type(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {

        String sql = """
                DELETE FROM loan_types
                WHERE loan_type_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, loanTypeId);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " loan type(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}