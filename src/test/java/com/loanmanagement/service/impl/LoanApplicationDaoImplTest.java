package com.loanmanagement;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.model.LoanApplication;
import org.junit.jupiter.api.Test;

public class LoanApplicationDaoImplTest {
    private LoanApplicationDao loanApplicationDao = new LoanApplicationDaoImpl();

    @Test
    public void testAddLoanApplication() {

        LoanApplication application = new LoanApplication();

        application.setCustomerId(23);
        application.setLoanTypeId(8);
        application.setRequestedAmount(200000);
        application.setTenureMonths(24);
        application.setPurpose("Home renovation");
        application.setStatus("PENDING");
        application.setRemarks("JUnit test application");

        loanApplicationDao.addLoanApplication(application);
    }

    @Test
    public void testGetLoanApplicationById() {

        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(13);

        System.out.println("Application ID: " + application.getApplicationId());
        System.out.println("Customer ID: " + application.getCustomerId());
        System.out.println("Requested Amount: " + application.getRequestedAmount());
    }

    @Test
    public void testUpdateLoanApplication() {

        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(13);

        application.setRequestedAmount(250000);
        application.setRemarks("Updated by JUnit test");
        application.setReviewedBy(18);
        loanApplicationDao.updateLoanApplication(application);
    }
    @Test
    public void testDeleteLoanApplication() {

        loanApplicationDao.deleteLoanApplication(13);
    }
}