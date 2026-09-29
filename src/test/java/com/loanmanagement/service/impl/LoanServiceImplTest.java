package com.loanmanagement;

import com.loanmanagement.model.Loan;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;

public class LoanServiceImplTest {
    private LoanService loanService = new LoanServiceImpl();
    @Test
    public void testAddLoan() {

        Loan loan = new Loan();

        loan.setApplicationId(2);
        loan.setCustomerId(1);
        loan.setLoanTypeId(2);
        loan.setPrincipalAmount(200000);
        loan.setInterestRate(10.5);
        loan.setTenureMonths(24);
        loan.setTotalPayable(242000);
        loan.setOutstandingAmount(242000);
        loan.setStartDate("2026-09-24");
        loan.setStatus("ACTIVE");
        loan.setCreatedBy(1);

        loanService.addLoan(loan);
    }
    @Test
    public void testGetLoanById() {

        Loan loan = loanService.getLoanById(4);

        System.out.println("Loan ID: " + loan.getLoanId());
        System.out.println("Customer ID: " + loan.getCustomerId());
        System.out.println("Principal Amount: " + loan.getPrincipalAmount());
        System.out.println("Outstanding Amount: " + loan.getOutstandingAmount());
    }
    @Test
    public void testUpdateLoan() {

        Loan loan = loanService.getLoanById(4);

        loan.setInterestRate(11.0);
        loan.setOutstandingAmount(240000);

        loanService.updateLoan(loan);
    }
    @Test
    public void testDeleteLoan() {

        loanService.deleteLoan(9999);
    }
}
