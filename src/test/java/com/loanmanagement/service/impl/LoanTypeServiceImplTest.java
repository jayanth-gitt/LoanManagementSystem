package com.loanmanagement;

import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanTypeServiceImplTest {
    private LoanTypeService loanTypeService =
            new LoanTypeServiceImpl();
    @Test
    public void testAddLoanType() {

        LoanType loanType = new LoanType();

        loanType.setName("Service Test Loan");
        loanType.setDescription("Loan for service testing");
        loanType.setInterestRate(10.5);
        loanType.setMinAmount(50000);
        loanType.setMaxAmount(500000);
        loanType.setMaxTenureMonths(60);
        loanType.setStatus("ACTIVE");

        loanTypeService.addLoanType(loanType);
    }
    @Test
    public void testGetLoanTypeById() {

        LoanType loanType = loanTypeService.getLoanTypeById(1);

        System.out.println("Loan Type ID: " + loanType.getLoanTypeId());
        System.out.println("Name: " + loanType.getName());
        System.out.println("Interest Rate: " + loanType.getInterestRate());
    }
    @Test
    public void testUpdateLoanType() {

        LoanType loanType = loanTypeService.getLoanTypeById(1);

        loanType.setInterestRate(11.0);
        loanType.setMaxAmount(600000);

        loanTypeService.updateLoanType(loanType);
    }
    @Test
    public void testDeleteLoanType() {

        loanTypeService.deleteLoanType(99);
    }
    @Test
    public void testAddLoanTypeWithInvalidAmount() {

        LoanType loanType = new LoanType();

        loanType.setName("Invalid Loan");
        loanType.setInterestRate(10.0);
        loanType.setMinAmount(600000);
        loanType.setMaxAmount(500000);
        loanType.setMaxTenureMonths(60);
        loanType.setStatus("ACTIVE");

        assertThrows(IllegalArgumentException.class, () -> {
            loanTypeService.addLoanType(loanType);
        });
    }

    @Test
    public void testAddLoanTypeWithInvalidTenure() {

        LoanType loanType = new LoanType();

        loanType.setName("Invalid Tenure Loan");
        loanType.setInterestRate(10.0);
        loanType.setMinAmount(50000);
        loanType.setMaxAmount(500000);
        loanType.setMaxTenureMonths(0);
        loanType.setStatus("ACTIVE");

        assertThrows(IllegalArgumentException.class, () -> {
            loanTypeService.addLoanType(loanType);
        });
    }
}
