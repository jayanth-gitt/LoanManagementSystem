package com.loanmanagement.dao;

import com.loanmanagement.model.LoanApplication;

public interface LoanApplicationDao {

    int addLoanApplication(LoanApplication application);

    LoanApplication getLoanApplicationById(int applicationId);

    int updateLoanApplication(LoanApplication application);

    int deleteLoanApplication(int applicationId);

    // Application workflow
    int approveApplication(
            int applicationId,
            int reviewedBy,
            String remarks
    );

    int rejectApplication(
            int applicationId,
            int reviewedBy,
            String remarks
    );
}