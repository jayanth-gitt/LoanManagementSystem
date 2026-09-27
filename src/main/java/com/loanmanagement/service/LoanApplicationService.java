package com.loanmanagement.service;

import com.loanmanagement.model.LoanApplication;

public interface LoanApplicationService {

    int addApplication(LoanApplication application);

    LoanApplication getApplicationById(int applicationId);

    int updateApplication(LoanApplication application);

    int approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks
    );

    int rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks
    );

    int deleteApplication(int applicationId);
}