package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanApplicationDaoImpl implements LoanApplicationDao {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanApplicationDaoImpl.class);


    // ============================================================
    // SQL CONSTANTS
    // ============================================================

    public static final String INSERT_LOAN_APPLICATION_SQL = """
            INSERT INTO loan_applications
            (
                customer_id,
                loan_type_id,
                requested_amount,
                tenure_months,
                purpose,
                status,
                remarks,
                reviewed_by,
                reviewed_at
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;


    public static final String SELECT_LOAN_APPLICATION_BY_ID_SQL = """
            SELECT *
            FROM loan_applications
            WHERE application_id = ?
            """;


    public static final String UPDATE_LOAN_APPLICATION_SQL = """
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
                reviewed_at = ?
            WHERE application_id = ?
            """;


    public static final String DELETE_LOAN_APPLICATION_SQL = """
            DELETE FROM loan_applications
            WHERE application_id = ?
            """;


    // ============================================================
    // APPROVE APPLICATION
    // ============================================================

    public static final String APPROVE_APPLICATION_SQL = """
            UPDATE loan_applications
            SET
                status = 'APPROVED',
                remarks = ?,
                reviewed_by = ?,
                reviewed_at = NOW()
            WHERE application_id = ?
              AND status = 'PENDING'
            """;


    // ============================================================
    // REJECT APPLICATION
    // ============================================================

    public static final String REJECT_APPLICATION_SQL = """
            UPDATE loan_applications
            SET
                status = 'REJECTED',
                remarks = ?,
                reviewed_by = ?,
                reviewed_at = NOW()
            WHERE application_id = ?
              AND status = 'PENDING'
            """;


    // ============================================================
    // ADD LOAN APPLICATION
    // ============================================================

    @Override
    public int addLoanApplication(LoanApplication application) {

        logger.info(
                "Adding loan application for customer ID: {}",
                application.getCustomerId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                INSERT_LOAN_APPLICATION_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    application.getCustomerId()
            );

            preparedStatement.setInt(
                    2,
                    application.getLoanTypeId()
            );

            preparedStatement.setDouble(
                    3,
                    application.getRequestedAmount()
            );

            preparedStatement.setInt(
                    4,
                    application.getTenureMonths()
            );

            preparedStatement.setString(
                    5,
                    application.getPurpose()
            );

            preparedStatement.setString(
                    6,
                    application.getStatus()
            );

            preparedStatement.setString(
                    7,
                    application.getRemarks()
            );

            preparedStatement.setInt(
                    8,
                    application.getReviewedBy()
            );

            preparedStatement.setString(
                    9,
                    application.getReviewedAt()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            logger.info(
                    "Loan application added successfully. Rows affected: {}",
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while adding loan application",
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // GET LOAN APPLICATION BY ID
    // ============================================================

    @Override
    public LoanApplication getLoanApplicationById(
            int applicationId
    ) {

        logger.info(
                "Fetching loan application with ID: {}",
                applicationId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                SELECT_LOAN_APPLICATION_BY_ID_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    applicationId
            );

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                if (resultSet.next()) {

                    LoanApplication application =
                            new LoanApplication(

                                    resultSet.getInt(
                                            "application_id"
                                    ),

                                    resultSet.getInt(
                                            "customer_id"
                                    ),

                                    resultSet.getInt(
                                            "loan_type_id"
                                    ),

                                    resultSet.getDouble(
                                            "requested_amount"
                                    ),

                                    resultSet.getInt(
                                            "tenure_months"
                                    ),

                                    resultSet.getString(
                                            "purpose"
                                    ),

                                    resultSet.getString(
                                            "status"
                                    ),

                                    resultSet.getString(
                                            "remarks"
                                    ),

                                    resultSet.getInt(
                                            "reviewed_by"
                                    ),

                                    resultSet.getString(
                                            "applied_at"
                                    ),

                                    resultSet.getString(
                                            "reviewed_at"
                                    )
                            );

                    logger.info(
                            "Loan application found with ID: {}",
                            applicationId
                    );

                    return application;
                }
            }

            logger.warn(
                    "No loan application found with ID: {}",
                    applicationId
            );

        } catch (SQLException e) {

            logger.error(
                    "Error while fetching loan application with ID: {}",
                    applicationId,
                    e
            );
        }

        return null;
    }


    // ============================================================
    // UPDATE LOAN APPLICATION
    // ============================================================

    @Override
    public int updateLoanApplication(
            LoanApplication application
    ) {

        logger.info(
                "Updating loan application with ID: {}",
                application.getApplicationId()
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                UPDATE_LOAN_APPLICATION_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    application.getCustomerId()
            );

            preparedStatement.setInt(
                    2,
                    application.getLoanTypeId()
            );

            preparedStatement.setDouble(
                    3,
                    application.getRequestedAmount()
            );

            preparedStatement.setInt(
                    4,
                    application.getTenureMonths()
            );

            preparedStatement.setString(
                    5,
                    application.getPurpose()
            );

            preparedStatement.setString(
                    6,
                    application.getStatus()
            );

            preparedStatement.setString(
                    7,
                    application.getRemarks()
            );

            preparedStatement.setInt(
                    8,
                    application.getReviewedBy()
            );

            preparedStatement.setString(
                    9,
                    application.getReviewedAt()
            );

            preparedStatement.setInt(
                    10,
                    application.getApplicationId()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan application with ID {} updated successfully. Rows affected: {}",
                        application.getApplicationId(),
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan application found to update with ID: {}",
                        application.getApplicationId()
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while updating loan application with ID: {}",
                    application.getApplicationId(),
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // DELETE LOAN APPLICATION
    // ============================================================

    @Override
    public int deleteLoanApplication(int applicationId) {

        logger.info(
                "Deleting loan application with ID: {}",
                applicationId
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                DELETE_LOAN_APPLICATION_SQL
                        )
        ) {

            preparedStatement.setInt(
                    1,
                    applicationId
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                logger.info(
                        "Loan application with ID {} deleted successfully. Rows affected: {}",
                        applicationId,
                        rowsAffected
                );

            } else {

                logger.warn(
                        "No loan application found to delete with ID: {}",
                        applicationId
                );
            }

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while deleting loan application with ID: {}",
                    applicationId,
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // APPROVE APPLICATION
    // ============================================================

    @Override
    public int approveApplication(
            int applicationId,
            int reviewedBy,
            String remarks
    ) {

        logger.info(
                "Approving loan application ID: {} by user ID: {}",
                applicationId,
                reviewedBy
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                APPROVE_APPLICATION_SQL
                        )
        ) {

            preparedStatement.setString(
                    1,
                    remarks
            );

            preparedStatement.setInt(
                    2,
                    reviewedBy
            );

            preparedStatement.setInt(
                    3,
                    applicationId
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            logger.info(
                    "Loan application approval completed. Rows affected: {}",
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while approving loan application ID: {}",
                    applicationId,
                    e
            );

            return 0;
        }
    }


    // ============================================================
    // REJECT APPLICATION
    // ============================================================

    @Override
    public int rejectApplication(
            int applicationId,
            int reviewedBy,
            String remarks
    ) {

        logger.info(
                "Rejecting loan application ID: {} by user ID: {}",
                applicationId,
                reviewedBy
        );

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                REJECT_APPLICATION_SQL
                        )
        ) {

            preparedStatement.setString(
                    1,
                    remarks
            );

            preparedStatement.setInt(
                    2,
                    reviewedBy
            );

            preparedStatement.setInt(
                    3,
                    applicationId
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            logger.info(
                    "Loan application rejection completed. Rows affected: {}",
                    rowsAffected
            );

            return rowsAffected;

        } catch (SQLException e) {

            logger.error(
                    "Error while rejecting loan application ID: {}",
                    applicationId,
                    e
            );

            return 0;
        }
    }
}