package com.loanmanagement;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.LoanType;
import org.junit.jupiter.api.Test;

public class LoanTypeDaoImplTest {
    LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();
    @Test
    public void testAddLoanType() {

        LoanType loanType = new LoanType();

        loanType.setName("Education Loan");
        loanType.setDescription("Loan for education");
        loanType.setInterestRate(8.5);
        loanType.setMinAmount(50000);
        loanType.setMaxAmount(500000);
        loanType.setMaxTenureMonths(60);
        loanType.setStatus("ACTIVE");

        loanTypeDao.addLoanType(loanType);
    }
    @Test
    public void testGetLoanTypeById() {

        LoanType loanType = loanTypeDao.getLoanTypeById(5);

        System.out.println("Loan Type ID: " + loanType.getLoanTypeId());
        System.out.println("Name: " + loanType.getName());
        System.out.println("Interest Rate: " + loanType.getInterestRate());
    }
    @Test
    public void testUpdateLoanType() {

        LoanType loanType = loanTypeDao.getLoanTypeById(5);

        loanType.setInterestRate(9.5);
        loanType.setMaxAmount(700000);

        loanTypeDao.updateLoanType(loanType);
    }
    @Test
    public void testDeleteLoanType() {

        loanTypeDao.deleteLoanType(6);
    }

}
