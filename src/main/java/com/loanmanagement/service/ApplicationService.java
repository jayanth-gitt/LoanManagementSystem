package com.loanmanagement.service;

import com.loanmanagement.model.LoanApplication;

public interface ApplicationService {
    int addApplication(LoanApplication application);

    LoanApplication getApplicationById(int applicationId);

    int updateApplication(LoanApplication application);

    int deleteApplication(int applicationId);
}
