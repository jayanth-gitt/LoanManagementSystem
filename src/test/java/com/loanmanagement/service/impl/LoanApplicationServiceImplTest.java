package com.loanmanagement;

import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.service.LoanApplicationService;
import com.loanmanagement.service.impl.LoanApplicationServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanApplicationServiceImplTest {
    private LoanApplicationService loanApplicationService =
            new LoanApplicationServiceImpl();
    @Test
    public void testAddLoanApplication() {

        LoanApplication application = new LoanApplication();

        application.setCustomerId(1);
        application.setLoanTypeId(2);
        application.setRequestedAmount(200000);
        application.setTenureMonths(24);
        application.setPurpose("Service test application");
        application.setStatus("PENDING");
        application.setRemarks("Created through service");

        loanApplicationService.addApplication(application);
    }
    @Test
    public void testGetLoanApplicationById() {

        LoanApplication application =
                loanApplicationService.getApplicationById(2);

        System.out.println("Application ID: " + application.getApplicationId());
        System.out.println("Customer ID: " + application.getCustomerId());
        System.out.println("Requested Amount: " + application.getRequestedAmount());
    }
    @Test
    public void testUpdateLoanApplication() {

        LoanApplication application =
                loanApplicationService.getApplicationById(2);

        application.setRequestedAmount(250000);
        application.setRemarks("Updated through service");
        application.setReviewedBy(1);
        application.setStatus("APPROVED");
        loanApplicationService.updateApplication(application);
    }
    @Test
    public void testDeleteLoanApplication() {

        loanApplicationService.deleteApplication(9999);
    }

    @Test
    public void testAddApplicationWithInvalidAmount() {

        LoanApplication application = new LoanApplication();

        application.setCustomerId(1);
        application.setLoanTypeId(2);
        application.setRequestedAmount(10000000);
        application.setTenureMonths(24);
        application.setPurpose("Invalid amount test");

        assertThrows(IllegalArgumentException.class, () -> {
            loanApplicationService.addApplication(application);
        });
    }
}
