package com.loanmanagement.controller;

import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.service.LoanApplicationService;
import com.loanmanagement.service.impl.LoanApplicationServiceImpl;

import java.util.List;

public class LoanApplicationController {
    private final LoanApplicationService loanApplicationService =
            new LoanApplicationServiceImpl();

    public void addApplication(LoanApplication application) {
        loanApplicationService.addApplication(application);
    }

    public LoanApplication getApplicationById(int applicationId) {
        return loanApplicationService.getApplicationById(applicationId);
    }

    public void updateApplication(LoanApplication application) {
        loanApplicationService.updateApplication(application);
    }

    public void approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        loanApplicationService.approveApplication(
                applicationId,
                loanOfficerId,
                remarks
        );
    }

    public void rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        loanApplicationService.rejectApplication(
                applicationId,
                loanOfficerId,
                remarks
        );
    }
    public List<LoanApplication> getAllApplications() {
        return loanApplicationService.getAllApplications();
    }

    public void deleteApplication(int applicationId) {
        loanApplicationService.deleteApplication(applicationId);
    }
}
