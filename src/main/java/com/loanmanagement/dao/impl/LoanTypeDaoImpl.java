package com.loanmanagement.dao.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoanTypeDaoImpl implements LoanTypeDao {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanTypeDaoImpl.class);
    private static final String statement = "INSERT INTO loan_types " +
            "(name, description, interest_rate, min_amount, max_amount, max_tenure_months, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String statement1 = "SELECT * FROM loan_types WHERE loan_type_id = ?";

    private static final   String statement2 = "UPDATE loan_types SET " +
            "name=?, description=?, interest_rate=?, min_amount=?, " +
            "max_amount=?, max_tenure_months=?, status=? " +
            "WHERE loan_type_id=?";
    private static final String statement3 = "DELETE FROM loan_types WHERE loan_type_id=?";
    @Override
    public void addLoanType(LoanType loanType) {


        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement);

            ps.setString(1, loanType.getName());
            ps.setString(2, loanType.getDescription());
            ps.setDouble(3, loanType.getInterestRate());
            ps.setDouble(4, loanType.getMinAmount());
            ps.setDouble(5, loanType.getMaxAmount());
            ps.setInt(6, loanType.getMaxTenureMonths());
            ps.setString(7, loanType.getStatus());

            ps.executeUpdate();

            logger.info("Loan type added successfully!");

        } catch (Exception e) {
            logger.error("error while adding LoanType", e);
            throw new RuntimeException("Failed to add loan type", e);
        }
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {

        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement1);
            ps.setInt(1, loanTypeId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                LoanType loanType = new LoanType();

                loanType.setLoanTypeId(rs.getInt("loan_type_id"));
                loanType.setName(rs.getString("name"));
                loanType.setDescription(rs.getString("description"));
                loanType.setInterestRate(rs.getDouble("interest_rate"));
                loanType.setMinAmount(rs.getDouble("min_amount"));
                loanType.setMaxAmount(rs.getDouble("max_amount"));
                loanType.setMaxTenureMonths(rs.getInt("max_tenure_months"));
                loanType.setStatus(rs.getString("status"));

                return loanType;
            }

        } catch (Exception e) {
            logger.error("error while getting LoanType", e);
            throw new RuntimeException("Failed to get loan type", e);
        }

        return null;
    }

    @Override
    public void updateLoanType(LoanType loanType) {


        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement2);

            ps.setString(1, loanType.getName());
            ps.setString(2, loanType.getDescription());
            ps.setDouble(3, loanType.getInterestRate());
            ps.setDouble(4, loanType.getMinAmount());
            ps.setDouble(5, loanType.getMaxAmount());
            ps.setInt(6, loanType.getMaxTenureMonths());
            ps.setString(7, loanType.getStatus());
            ps.setInt(8, loanType.getLoanTypeId());

            ps.executeUpdate();

            logger.info("Loan type updated successfully!");

        } catch (Exception e) {
            logger.error("error while updating LoanType", e);
            throw new RuntimeException("Failed to update loan type", e);
        }
    }

    @Override
    public void deleteLoanType(int loanTypeId) {



        try {
            Connection con = new DBConnection().getConnection();

            PreparedStatement ps = con.prepareStatement(statement3);

            ps.setInt(1, loanTypeId);

            ps.executeUpdate();

            logger.info("Loan type deleted successfully!");

        } catch (Exception e) {
            logger.error("error while deleting loanType", e);
            throw new RuntimeException("Failed to delete loan type", e);
        }
    }
    @Override
    public List<LoanType> getAllLoanTypes() {

        List<LoanType> loanTypes = new ArrayList<>();

        String sql = "SELECT * FROM loan_types";

        try (Connection con = new DBConnection().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                LoanType loanType = new LoanType();

                loanType.setLoanTypeId(rs.getInt("loan_type_id"));
                loanType.setName(rs.getString("name"));
                loanType.setDescription(rs.getString("description"));
                loanType.setInterestRate(rs.getDouble("interest_rate"));
                loanType.setMinAmount(rs.getDouble("min_amount"));
                loanType.setMaxAmount(rs.getDouble("max_amount"));
                loanType.setMaxTenureMonths(
                        rs.getInt("max_tenure_months"));
                loanType.setStatus(rs.getString("status"));

                loanTypes.add(loanType);
            }

            logger.info("All loan types retrieved successfully");

        } catch (Exception e) {

            logger.error("Error while retrieving loan types", e);

            throw new RuntimeException(
                    "Failed to retrieve loan types", e);
        }

        return loanTypes;
    }
}
