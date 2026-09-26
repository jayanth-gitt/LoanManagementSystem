package com.loanmanagement.service;

import com.loanmanagement.model.LoanApplication;

public interface LoanApplicationService {

    void addApplication(LoanApplication application);

    LoanApplication getApplicationById(int applicationId);

    void updateApplication(LoanApplication application);

    void approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks
    );

    void rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks
    );

    void deleteApplication(int applicationId);
}