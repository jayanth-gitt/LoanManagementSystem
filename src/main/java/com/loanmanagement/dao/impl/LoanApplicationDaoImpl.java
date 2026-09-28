package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoanApplicationDaoImpl implements LoanApplicationDao {
    private static final Logger logger =
            LoggerFactory.getLogger(LoanApplicationDaoImpl.class);

    private static final String statement = "INSERT INTO loan_applications " +
            "(customer_id, loan_type_id, requested_amount, tenure_months, purpose, status, remarks) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String statement1 = "SELECT * FROM loan_applications WHERE application_id = ?";
    private static final String statement2 = "UPDATE loan_applications SET " +
            "customer_id=?, loan_type_id=?, requested_amount=?, " +
            "tenure_months=?, purpose=?, status=?, remarks=?, " +
            "reviewed_by=?, reviewed_at=? " +
            "WHERE application_id=?";
    private static final String statement3 = "DELETE FROM loan_applications WHERE application_id=?";
    private static final String sql = "SELECT COUNT(*) FROM loan_applications WHERE loan_type_id=?";




    @Override
    public void addLoanApplication(LoanApplication application) {


        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(
                    statement,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, application.getCustomerId());
            ps.setInt(2, application.getLoanTypeId());
            ps.setDouble(3, application.getRequestedAmount());
            ps.setInt(4, application.getTenureMonths());
            ps.setString(5, application.getPurpose());
            ps.setString(6, application.getStatus());
            ps.setString(7, application.getRemarks());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                application.setApplicationId(rs.getInt(1));
            }

            logger.info("Loan application added successfully!");

        } catch (Exception e) {
            logger.error("error while adding Loan application", e);
            throw new RuntimeException("Failed to add loan application", e);
        }
    }

    @Override
    public LoanApplication getLoanApplicationById(int applicationId) {


        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement1);

            ps.setInt(1, applicationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                LoanApplication application = new LoanApplication();

                application.setApplicationId(rs.getInt("application_id"));
                application.setCustomerId(rs.getInt("customer_id"));
                application.setLoanTypeId(rs.getInt("loan_type_id"));
                application.setRequestedAmount(rs.getDouble("requested_amount"));
                application.setTenureMonths(rs.getInt("tenure_months"));
                application.setPurpose(rs.getString("purpose"));
                application.setStatus(rs.getString("status"));
                application.setRemarks(rs.getString("remarks"));
                application.setReviewedBy(rs.getInt("reviewed_by"));
                application.setAppliedAt(rs.getString("applied_at"));
                application.setReviewedAt(rs.getString("reviewed_at"));

                return application;
            }

        } catch (Exception e) {
            logger.error("error while getting loan application ", e);
            throw new RuntimeException("Failed to get loan application", e);
        }

        return null;
    }

    @Override
    public void updateLoanApplication(LoanApplication application) {

        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement2);

            ps.setInt(1, application.getCustomerId());
            ps.setInt(2, application.getLoanTypeId());
            ps.setDouble(3, application.getRequestedAmount());
            ps.setInt(4, application.getTenureMonths());
            ps.setString(5, application.getPurpose());
            ps.setString(6, application.getStatus());
            ps.setString(7, application.getRemarks());
            ps.setInt(8, application.getReviewedBy());
            ps.setString(9, application.getReviewedAt());
            ps.setInt(10, application.getApplicationId());

            ps.executeUpdate();

            logger.info("Loan application updated successfully!");

        } catch (Exception e) {
            logger.error("error while updating Loan Application", e);
            throw new RuntimeException("Failed to update loan application", e);
        }

    }

    @Override
    public void deleteLoanApplication(int applicationId) {


        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement3);

            ps.setInt(1, applicationId);

            ps.executeUpdate();

            logger.info("Loan application deleted successfully!");

        } catch (Exception e) {
            logger.error("error while deleting Loan Application", e);
            throw new RuntimeException("Failed to delete loan application", e);
        }
    }
    @Override
    public boolean existsByLoanTypeId(int loanTypeId) {
        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, loanTypeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            logger.error("Error while checking loan type usage", e);
            throw new RuntimeException("Failed to check loan type usage", e);
        }
        return false;
    }
    @Override
    public List<LoanApplication> getAllApplications() {

        List<LoanApplication> applications = new ArrayList<>();

        String sql = "SELECT * FROM loan_applications";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                LoanApplication application = new LoanApplication();

                application.setApplicationId(
                        rs.getInt("application_id"));
                application.setCustomerId(
                        rs.getInt("customer_id"));
                application.setLoanTypeId(
                        rs.getInt("loan_type_id"));
                application.setRequestedAmount(
                        rs.getDouble("requested_amount"));
                application.setTenureMonths(
                        rs.getInt("tenure_months"));
                application.setPurpose(
                        rs.getString("purpose"));
                application.setStatus(
                        rs.getString("status"));
                application.setRemarks(
                        rs.getString("remarks"));
                application.setReviewedBy(
                        rs.getInt("reviewed_by"));
                application.setAppliedAt(
                        rs.getString("applied_at"));
                application.setReviewedAt(
                        rs.getString("reviewed_at"));

                applications.add(application);
            }

            logger.info("All loan applications retrieved successfully");

        } catch (Exception e) {

            logger.error(
                    "Error while retrieving loan applications", e);

            throw new RuntimeException(
                    "Failed to retrieve loan applications", e);
        }

        return applications;
    }

}

