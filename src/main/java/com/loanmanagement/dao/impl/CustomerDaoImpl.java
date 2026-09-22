package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.util.DBConnection;
import com.loanmanagement.model.CustomerStatus;
import com.loanmanagement.model.KycStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public void addCustomer(Customer customer) {
        String sql= """
                insert into customers(
                user_id,full_name,email,phone,dob,address,monthly_income,
                pan_number,aadhaar_last4,employment_type,account_number,
                ifsc_code,bank_name,kyc_status,kyc_remarks,kyc_verified_by,
                kyc_verified_at,credit_score,existing_emi,status)
                values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;
        try(Connection connection= DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getDob());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setDouble(7, customer.getMonthlyIncome());
            preparedStatement.setString(8, customer.getPanNumber());
            preparedStatement.setString(9, customer.getAadhaarLast4());
            preparedStatement.setString(10, customer.getEmploymentType());
            preparedStatement.setString(11, customer.getAccountNumber());
            preparedStatement.setString(12, customer.getIfscCode());
            preparedStatement.setString(13, customer.getBankName());
            preparedStatement.setString(14, customer.getKycStatus().name());
            preparedStatement.setString(15, customer.getKycRemarks());
            preparedStatement.setInt(16, customer.getKycVerifiedBy());
            preparedStatement.setString(17, customer.getKycVerifiedAt());
            preparedStatement.setInt(18, customer.getCreditScore());
            preparedStatement.setDouble(19, customer.getExistingEmi());
            preparedStatement.setString(20, customer.getStatus().name());

            preparedStatement.executeUpdate();

            System.out.println("customer added successfully.");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        String sql= """
                select * from customers where customer_id=?
                """;
        try(Connection connection=DBConnection.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1,customerId);

            ResultSet resultSet=preparedStatement.executeQuery();

            if (resultSet.next()){
                return new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("dob"),
                        resultSet.getString("address"),
                        resultSet.getDouble("monthly_income"),
                        resultSet.getString("pan_number"),
                        resultSet.getString("aadhaar_last4"),
                        resultSet.getString("employment_type"),
                        resultSet.getString("account_number"),
                        resultSet.getString("ifsc_code"),
                        resultSet.getString("bank_name"),

                        KycStatus.valueOf(
                                resultSet.getString("kyc_status")
                        ),

                        resultSet.getString("kyc_remarks"),
                        resultSet.getInt("kyc_verified_by"),
                        resultSet.getString("kyc_verified_at"),
                        resultSet.getInt("credit_score"),
                        resultSet.getDouble("existing_emi"),

                        CustomerStatus.valueOf(
                                resultSet.getString("status")
                        )
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = """
                UPDATE customers
                SET
                    user_id = ?,
                    full_name = ?,
                    email = ?,
                    phone = ?,
                    dob = ?,
                    address = ?,
                    monthly_income = ?,
                    pan_number = ?,
                    aadhaar_last4 = ?,
                    employment_type = ?,
                    account_number = ?,
                    ifsc_code = ?,
                    bank_name = ?,
                    kyc_status = ?,
                    kyc_remarks = ?,
                    kyc_verified_by = ?,
                    kyc_verified_at = ?,
                    credit_score = ?,
                    existing_emi = ?,
                    status = ?
                WHERE customer_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, customer.getUserId());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getDob());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setDouble(7, customer.getMonthlyIncome());
            preparedStatement.setString(8, customer.getPanNumber());
            preparedStatement.setString(9, customer.getAadhaarLast4());
            preparedStatement.setString(10, customer.getEmploymentType());
            preparedStatement.setString(11, customer.getAccountNumber());
            preparedStatement.setString(12, customer.getIfscCode());
            preparedStatement.setString(13, customer.getBankName());
            preparedStatement.setString(14, customer.getKycStatus().name());
            preparedStatement.setString(15, customer.getKycRemarks());
            preparedStatement.setInt(16, customer.getKycVerifiedBy());
            preparedStatement.setString(17, customer.getKycVerifiedAt());
            preparedStatement.setInt(18, customer.getCreditScore());
            preparedStatement.setDouble(19, customer.getExistingEmi());
            preparedStatement.setString(20, customer.getStatus().name());
            preparedStatement.setInt(21, customer.getCustomerId());

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " customer(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCustomer(int customerId) {
        String sql = """
                DELETE FROM customers
                WHERE customer_id = ?
                """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, customerId);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " customer(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    }
