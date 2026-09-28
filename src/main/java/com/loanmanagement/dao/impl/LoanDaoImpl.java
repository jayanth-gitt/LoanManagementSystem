package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.model.Loan;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoImpl implements LoanDao {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanDaoImpl.class);
    private static final  String statement = "INSERT INTO loans " +
            "(application_id, customer_id, loan_type_id, principal_amount, " +
            "interest_rate, tenure_months, total_payable, outstanding_amount, " +
            "start_date, status, created_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String sql = "SELECT * FROM loans WHERE application_id=?";



    private static final  String statement1 = "SELECT * FROM loans WHERE loan_id = ?";
    private static final  String statement2 = "UPDATE loans SET " +
            "application_id=?, customer_id=?, loan_type_id=?, " +
            "principal_amount=?, interest_rate=?, tenure_months=?, " +
            "total_payable=?, outstanding_amount=?, start_date=?, " +
            "status=?, created_by=? WHERE loan_id=?";

    private static final  String statement3= "DELETE FROM loans WHERE loan_id=?";
    @Override
    public void addLoan(Loan loan) {

        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(
                    statement,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, loan.getApplicationId());
            ps.setInt(2, loan.getCustomerId());
            ps.setInt(3, loan.getLoanTypeId());
            ps.setDouble(4, loan.getPrincipalAmount());
            ps.setDouble(5, loan.getInterestRate());
            ps.setInt(6, loan.getTenureMonths());
            ps.setDouble(7, loan.getTotalPayable());
            ps.setDouble(8, loan.getOutstandingAmount());
            ps.setString(9, loan.getStartDate());
            ps.setString(10, loan.getStatus());
            ps.setInt(11, loan.getCreatedBy());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                loan.setLoanId(rs.getInt(1));
            }

            logger.info("Loan added successfully!");

        } catch (Exception e) {
            logger.error("error while adding loan", e);
            throw new RuntimeException("Failed to add loan", e);
        }
    }


    @Override
    public Loan getLoanById(int loanId) {

        try {

            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement1);

            ps.setInt(1, loanId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();

                loan.setLoanId(rs.getInt("loan_id"));
                loan.setApplicationId(rs.getInt("application_id"));
                loan.setCustomerId(rs.getInt("customer_id"));
                loan.setLoanTypeId(rs.getInt("loan_type_id"));
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTenureMonths(rs.getInt("tenure_months"));
                loan.setTotalPayable(rs.getDouble("total_payable"));
                loan.setOutstandingAmount(rs.getDouble("outstanding_amount"));
                loan.setStartDate(rs.getString("start_date"));
                loan.setStatus(rs.getString("status"));
                loan.setCreatedBy(rs.getInt("created_by"));

                return loan;
            }
        } catch (Exception e) {
            logger.error("error while getting loan", e);
            throw new RuntimeException("Failed to get loan", e);
        }
        return null;
    }

    @Override
    public void updateLoan(Loan loan) {



        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement2);

            ps.setInt(1, loan.getApplicationId());
            ps.setInt(2, loan.getCustomerId());
            ps.setInt(3, loan.getLoanTypeId());
            ps.setDouble(4, loan.getPrincipalAmount());
            ps.setDouble(5, loan.getInterestRate());
            ps.setInt(6, loan.getTenureMonths());
            ps.setDouble(7, loan.getTotalPayable());
            ps.setDouble(8, loan.getOutstandingAmount());
            ps.setString(9, loan.getStartDate());
            ps.setString(10, loan.getStatus());
            ps.setInt(11, loan.getCreatedBy());
            ps.setInt(12, loan.getLoanId());

            ps.executeUpdate();

            logger.info("Loan updated successfully!");

        } catch (Exception e) {
            logger.error("error while updating Loan", e);
            throw new RuntimeException("Failed to update loan", e);

        }
    }

    @Override
    public void deleteLoan ( int loanId){


        try {
            Connection con = new DBConnection().getConnection();
            PreparedStatement ps = con.prepareStatement(statement3);

            ps.setInt(1, loanId);

            ps.executeUpdate();

            logger.info("Loan deleted successfully!");

        } catch (Exception e) {
            logger.error("error while deleting Loan", e);
            throw new RuntimeException("Failed to delete loan", e);
        }
    }
    @Override
    public Loan getLoanByApplicationId(int applicationId) {
        try (Connection con =new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, applicationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();

                loan.setLoanId(rs.getInt("loan_id"));
                loan.setApplicationId(rs.getInt("application_id"));
                loan.setCustomerId(rs.getInt("customer_id"));
                loan.setLoanTypeId(rs.getInt("loan_type_id"));
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTenureMonths(rs.getInt("tenure_months"));
                loan.setTotalPayable(rs.getDouble("total_payable"));
                loan.setOutstandingAmount(rs.getDouble("outstanding_amount"));
                loan.setStartDate(rs.getString("start_date"));
                loan.setStatus(rs.getString("status"));
                loan.setCreatedBy(rs.getInt("created_by"));

                return loan;
            }

        } catch (Exception e) {
            logger.error("Error while getting loan by application ID", e);
            throw new RuntimeException("Failed to get loan by application ID", e);
        }
        return null;
    }
    @Override
    public List<Loan> getAllLoans() {

        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT * FROM loans";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanId(rs.getInt("loan_id"));
                loan.setApplicationId(rs.getInt("application_id"));
                loan.setCustomerId(rs.getInt("customer_id"));
                loan.setLoanTypeId(rs.getInt("loan_type_id"));
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setTenureMonths(rs.getInt("tenure_months"));
                loan.setTotalPayable(rs.getDouble("total_payable"));
                loan.setOutstandingAmount(rs.getDouble("outstanding_amount"));
                loan.setStartDate(rs.getString("start_date"));
                loan.setStatus(rs.getString("status"));
                loan.setCreatedBy(rs.getInt("created_by"));

                loans.add(loan);
            }

            logger.info("All loans retrieved successfully");

        } catch (Exception e) {

            logger.error("Error while retrieving loans", e);

            throw new RuntimeException(
                    "Failed to retrieve loans", e);
        }

        return loans;
    }
}

