package com.loanmanagement;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.model.Loan;
import org.junit.jupiter.api.Test;

public class LoanDaoTest {
    private LoanDao loanDao = new LoanDaoImpl();

    @Test
    public void testAddLoan() {

        Loan loan = new Loan();

        loan.setApplicationId(15);
        loan.setCustomerId(23);
        loan.setLoanTypeId(8);
        loan.setPrincipalAmount(200000);
        loan.setInterestRate(10.5);
        loan.setTenureMonths(24);
        loan.setTotalPayable(242000);
        loan.setOutstandingAmount(242000);
        loan.setStartDate("2026-09-22");
        loan.setStatus("ACTIVE");
        loan.setCreatedBy(19);

        loanDao.addLoan(loan);
    }
    @Test
    public void testGetLoanById() {

        Loan loan = loanDao.getLoanById(9);

        System.out.println("Loan ID: " + loan.getLoanId());
        System.out.println("Customer ID: " + loan.getCustomerId());
        System.out.println("Principal Amount: " + loan.getPrincipalAmount());
        System.out.println("Outstanding Amount: " + loan.getOutstandingAmount());
    }

    @Test
    public void testUpdateLoan() {

        Loan loan = loanDao.getLoanById(9);

        loan.setInterestRate(11.0);
        loan.setOutstandingAmount(240000);

        loanDao.updateLoan(loan);
    }
    @Test
    public void testDeleteLoan() {

        loanDao.deleteLoan(9);
    }
}