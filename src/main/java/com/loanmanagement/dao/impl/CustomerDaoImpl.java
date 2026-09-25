package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.model.Customer;
import com.loanmanagement.model.CustomerStatus;
import com.loanmanagement.model.KycStatus;
import com.loanmanagement.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomerDaoImpl.class);

    // ============================================================
    // SQL CONSTANTS
    // ============================================================

    public static final String INSERT_CUSTOMER_SQL = """
            INSERT INTO customers(
                user_id,
                full_name,
                email,
                phone,
                dob,
                address,
                monthly_income,
                pan_number,
                aadhaar_last4,
                employment_type,
                account_number,
                ifsc_code,
                bank_name,
                kyc_status,
                kyc_remarks,
                kyc_verified_by,
                kyc_verified_at,
                credit_score,
                existing_emi,
                status
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
                    ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String SELECT_CUSTOMER_BY_ID_SQL = """
            SELECT *
            FROM customers
            WHERE customer_id = ?
            """;

    public static final String UPDATE_CUSTOMER_SQL = """
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

    public static final String DELETE_CUSTOMER_SQL = """
            DELETE FROM customers
            WHERE customer_id = ?
            """;


    // ============================================================
    // ADD CUSTOMER
    // ============================================================

    @Override
    public int addCustomer(Customer customer) {

        logger.info(
                "Adding customer: {}",
                customer.getFullName()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(INSERT_CUSTOMER_SQL)
        ) {

            preparedStatement.setInt(
                    1,
                    customer.getUserId()
            );

            preparedStatement.setString(
                    2,
                    customer.getFullName()
            );

            preparedStatement.setString(
                    3,
                    customer.getEmail()
            );

            preparedStatement.setString(
                    4,
                    customer.getPhone()
            );

            preparedStatement.setString(
                    5,
                    customer.getDob()
            );

            preparedStatement.setString(
                    6,
                    customer.getAddress()
            );

            preparedStatement.setDouble(
                    7,
                    customer.getMonthlyIncome()
            );

            preparedStatement.setString(
                    8,
                    customer.getPanNumber()
            );

            preparedStatement.setString(
                    9,
                    customer.getAadhaarLast4()
            );

            preparedStatement.setString(
                    10,
                    customer.getEmploymentType()
            );

            preparedStatement.setString(
                    11,
                    customer.getAccountNumber()
            );

            preparedStatement.setString(
                    12,
                    customer.getIfscCode()
            );

            preparedStatement.setString(
                    13,
                    customer.getBankName()
            );

            preparedStatement.setString(
                    14,
                    customer.getKycStatus().name()
            );

            preparedStatement.setString(
                    15,
                    customer.getKycRemarks()
            );

            preparedStatement.setInt(
                    16,
                    customer.getKycVerifiedBy()
            );

            preparedStatement.setString(
                    17,
                    customer.getKycVerifiedAt()
            );

            preparedStatement.setInt(
                    18,
                    customer.getCreditScore()
            );

            preparedStatement.setDouble(
                    19,
                    customer.getExistingEmi()
            );

            preparedStatement.setString(
                    20,
                    customer.getStatus().name()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            logger.info(
                    "Customer '{}' added successfully. Rows affected: {}",
                    customer.getFullName(),
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while adding customer: {}",
                    customer.getFullName(),
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // GET CUSTOMER BY ID
    // ============================================================

    @Override
    public Customer getCustomerById(int customerId) {

        logger.info(
                "Fetching customer with ID: {}",
                customerId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                SELECT_CUSTOMER_BY_ID_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    customerId
            );

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                if (resultSet.next()) {

                    Customer customer = new Customer(

                            resultSet.getInt(
                                    "customer_id"
                            ),

                            resultSet.getInt(
                                    "user_id"
                            ),

                            resultSet.getString(
                                    "full_name"
                            ),

                            resultSet.getString(
                                    "email"
                            ),

                            resultSet.getString(
                                    "phone"
                            ),

                            resultSet.getString(
                                    "dob"
                            ),

                            resultSet.getString(
                                    "address"
                            ),

                            resultSet.getDouble(
                                    "monthly_income"
                            ),

                            resultSet.getString(
                                    "pan_number"
                            ),

                            resultSet.getString(
                                    "aadhaar_last4"
                            ),

                            resultSet.getString(
                                    "employment_type"
                            ),

                            resultSet.getString(
                                    "account_number"
                            ),

                            resultSet.getString(
                                    "ifsc_code"
                            ),

                            resultSet.getString(
                                    "bank_name"
                            ),

                            KycStatus.valueOf(
                                    resultSet.getString(
                                            "kyc_status"
                                    )
                            ),

                            resultSet.getString(
                                    "kyc_remarks"
                            ),

                            resultSet.getInt(
                                    "kyc_verified_by"
                            ),

                            resultSet.getString(
                                    "kyc_verified_at"
                            ),

                            resultSet.getInt(
                                    "credit_score"
                            ),

                            resultSet.getDouble(
                                    "existing_emi"
                            ),

                            CustomerStatus.valueOf(
                                    resultSet.getString(
                                            "status"
                                    )
                            )
                    );

                    logger.info(
                            "Customer found with ID: {}",
                            customerId
                    );

                    return customer;
                }
            }

            logger.warn(
                    "No customer found with ID: {}",
                    customerId
            );

        } catch (SQLException e) {

            logger.error(
                    "Error while fetching customer with ID: {}",
                    customerId,
                    e
            );
        }

        return null;
    }


    // ============================================================
    // UPDATE CUSTOMER
    // ============================================================

    @Override
    public int updateCustomer(Customer customer) {

        logger.info(
                "Updating customer with ID: {}",
                customer.getCustomerId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                UPDATE_CUSTOMER_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    customer.getUserId()
            );

            preparedStatement.setString(
                    2,
                    customer.getFullName()
            );

            preparedStatement.setString(
                    3,
                    customer.getEmail()
            );

            preparedStatement.setString(
                    4,
                    customer.getPhone()
            );

            preparedStatement.setString(
                    5,
                    customer.getDob()
            );

            preparedStatement.setString(
                    6,
                    customer.getAddress()
            );

            preparedStatement.setDouble(
                    7,
                    customer.getMonthlyIncome()
            );

            preparedStatement.setString(
                    8,
                    customer.getPanNumber()
            );

            preparedStatement.setString(
                    9,
                    customer.getAadhaarLast4()
            );

            preparedStatement.setString(
                    10,
                    customer.getEmploymentType()
            );

            preparedStatement.setString(
                    11,
                    customer.getAccountNumber()
            );

            preparedStatement.setString(
                    12,
                    customer.getIfscCode()
            );

            preparedStatement.setString(
                    13,
                    customer.getBankName()
            );

            preparedStatement.setString(
                    14,
                    customer.getKycStatus().name()
            );

            preparedStatement.setString(
                    15,
                    customer.getKycRemarks()
            );

            preparedStatement.setInt(
                    16,
                    customer.getKycVerifiedBy()
            );

            preparedStatement.setString(
                    17,
                    customer.getKycVerifiedAt()
            );

            preparedStatement.setInt(
                    18,
                    customer.getCreditScore()
            );

            preparedStatement.setDouble(
                    19,
                    customer.getExistingEmi()
            );

            preparedStatement.setString(
                    20,
                    customer.getStatus().name()
            );

            preparedStatement.setInt(
                    21,
                    customer.getCustomerId()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Customer with ID {} updated successfully. Rows affected: {}",
                        customer.getCustomerId(),
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No customer found to update with ID: {}",
                        customer.getCustomerId()
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while updating customer with ID: {}",
                    customer.getCustomerId(),
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // DELETE CUSTOMER
    // ============================================================

    @Override
    public int deleteCustomer(int customerId) {

        logger.info(
                "Deleting customer with ID: {}",
                customerId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                DELETE_CUSTOMER_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    customerId
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Customer with ID {} deleted successfully. Rows affected: {}",
                        customerId,
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No customer found to delete with ID: {}",
                        customerId
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting customer with ID: {}",
                    customerId,
                    e
            );

            return 0;
        }
    }
}