package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private static final Logger logger =
            LoggerFactory.getLogger(CustomerDaoImpl.class);
    private static final String statement = "INSERT INTO customers " +
            "(user_id, full_name, email, phone, dob, address, monthly_income, " +
            "pan_number, aadhaar_last4, employment_type, account_number, " +
            "ifsc_code, bank_name, kyc_status, kyc_remarks, kyc_verified_by, " +
            "kyc_verified_at, credit_score, existing_emi, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String statement1 = "SELECT * FROM customers WHERE customer_id = ?";
    private static final String statement2 = "UPDATE customers SET " +
            "user_id=?, full_name=?, email=?, phone=?, dob=?, address=?, " +
            "monthly_income=?, pan_number=?, aadhaar_last4=?, employment_type=?, " +
            "account_number=?, ifsc_code=?, bank_name=?, kyc_status=?, " +
            "kyc_remarks=?, kyc_verified_by=?, kyc_verified_at=?, " +
            "credit_score=?, existing_emi=?, status=? " +
            "WHERE customer_id=?";

    private static final String statement3="Delete FROM customers WHERE customer_id=?";
    @Override
    public void addCustomer(Customer customer) {
        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(
                    statement,
                    java.sql.Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getDob());
            ps.setString(6, customer.getAddress());
            ps.setDouble(7, customer.getMonthlyIncome());
            ps.setString(8, customer.getPanNumber());
            ps.setString(9, customer.getAadhaarLast4());
            ps.setString(10, customer.getEmploymentType());
            ps.setString(11, customer.getAccountNumber());
            ps.setString(12, customer.getIfscCode());
            ps.setString(13, customer.getBankName());
            ps.setString(14, customer.getKycStatus());
            ps.setString(15, customer.getKycRemarks());

            if (customer.getKycVerifiedBy() == null) {
                ps.setNull(16, java.sql.Types.INTEGER);
            } else {
                ps.setInt(16, customer.getKycVerifiedBy());
            }

            ps.setString(17, customer.getKycVerifiedAt());
            ps.setInt(18, customer.getCreditScore());
            ps.setDouble(19, customer.getExistingEmi());
            ps.setString(20, customer.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setCustomerId(rs.getInt(1));
                }
            }

            logger.info("Customer added successfully!");

        } catch (Exception e) {
            logger.error("Error while adding customer", e);
            throw new RuntimeException("Failed to add customer", e);
        }

    }

    @Override
    public Customer getCustomerById(int customerId) {

        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement1);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setUserId(rs.getInt("user_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setDob(rs.getString("dob"));
                customer.setAddress(rs.getString("address"));
                customer.setMonthlyIncome(rs.getDouble("monthly_income"));
                customer.setPanNumber(rs.getString("pan_number"));
                customer.setAadhaarLast4(rs.getString("aadhaar_last4"));
                customer.setEmploymentType(rs.getString("employment_type"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setIfscCode(rs.getString("ifsc_code"));
                customer.setBankName(rs.getString("bank_name"));
                customer.setKycStatus(rs.getString("kyc_status"));
                customer.setKycRemarks(rs.getString("kyc_remarks"));
                int kycVerifiedBy = rs.getInt("kyc_verified_by");

                if (rs.wasNull()) {
                    customer.setKycVerifiedBy(null);
                } else {
                    customer.setKycVerifiedBy(kycVerifiedBy);
                }
                customer.setKycVerifiedAt(rs.getString("kyc_verified_at"));
                customer.setCreditScore(rs.getInt("credit_score"));
                customer.setExistingEmi(rs.getDouble("existing_emi"));
                customer.setStatus(rs.getString("status"));

                return customer;
            }

        } catch (Exception e) {
            logger.error("error while getting customer", e);
            throw new RuntimeException("Failed to get customer", e);
        }

        return null;
    }

    @Override
    public void updateCustomer(Customer customer) {


        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement2);

            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getDob());
            ps.setString(6, customer.getAddress());
            ps.setDouble(7, customer.getMonthlyIncome());
            ps.setString(8, customer.getPanNumber());
            ps.setString(9, customer.getAadhaarLast4());
            ps.setString(10, customer.getEmploymentType());
            ps.setString(11, customer.getAccountNumber());
            ps.setString(12, customer.getIfscCode());
            ps.setString(13, customer.getBankName());
            ps.setString(14, customer.getKycStatus());
            ps.setString(15, customer.getKycRemarks());
            if (customer.getKycVerifiedBy() == null) {
                ps.setNull(16, java.sql.Types.INTEGER);
            } else {
                ps.setInt(16, customer.getKycVerifiedBy());
            }
            ps.setString(17, customer.getKycVerifiedAt());
            ps.setInt(18, customer.getCreditScore());
            ps.setDouble(19, customer.getExistingEmi());
            ps.setString(20, customer.getStatus());
            ps.setInt(21, customer.getCustomerId());

            ps.executeUpdate();

            logger.info("Customer updated successfully!");

        } catch (Exception e) {
            logger.error("error while updating customer", e);
            throw new RuntimeException("Failed to update customer", e);
        }
    }
    @Override
    public void deleteCustomer( int customerId){

        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement3);

            ps.setInt(1, customerId);

            ps.executeUpdate();

            logger.info("Customer deleted successfully!");

        } catch (Exception e) {
            logger.error("error while deleting customer", e);
            throw new RuntimeException("Failed to delete customer", e);
        }

    }
    @Override
    public List <Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setUserId(rs.getInt("user_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setDob(rs.getString("dob"));
                customer.setAddress(rs.getString("address"));
                customer.setMonthlyIncome(rs.getDouble("monthly_income"));
                customer.setPanNumber(rs.getString("pan_number"));
                customer.setAadhaarLast4(rs.getString("aadhaar_last4"));
                customer.setEmploymentType(rs.getString("employment_type"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setIfscCode(rs.getString("ifsc_code"));
                customer.setBankName(rs.getString("bank_name"));
                customer.setKycStatus(rs.getString("kyc_status"));
                customer.setKycRemarks(rs.getString("kyc_remarks"));

                int kycVerifiedBy = rs.getInt("kyc_verified_by");
                if (rs.wasNull()) {
                    customer.setKycVerifiedBy(null);
                } else {
                    customer.setKycVerifiedBy(kycVerifiedBy);
                }

                customer.setKycVerifiedAt(rs.getString("kyc_verified_at"));
                customer.setCreditScore(rs.getInt("credit_score"));
                customer.setExistingEmi(rs.getDouble("existing_emi"));
                customer.setStatus(rs.getString("status"));

                customers.add(customer);
            }

            logger.info("All customers retrieved successfully");

        } catch (Exception e) {

            logger.error("Error while retrieving customers", e);

            throw new RuntimeException(
                    "Failed to retrieve customers", e);
        }

        return customers;
    }
    @Override
    public Customer getCustomerByUsername(String username) {

        String sql = "SELECT c.* " +
                "FROM customers c " +
                "JOIN users u ON c.user_id = u.user_id " +
                "WHERE u.username = ?";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Customer customer = new Customer();

                    customer.setCustomerId(
                            rs.getInt("customer_id"));

                    customer.setUserId(
                            rs.getInt("user_id"));

                    customer.setFullName(
                            rs.getString("full_name"));

                    customer.setEmail(
                            rs.getString("email"));

                    customer.setPhone(
                            rs.getString("phone"));

                    return customer;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error fetching customer by username", e);
        }

        return null;
    }
}

