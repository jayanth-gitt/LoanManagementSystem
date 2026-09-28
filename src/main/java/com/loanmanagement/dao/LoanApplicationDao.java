package com.loanmanagement.dao;

import com.loanmanagement.model.LoanApplication;

import java.util.List;

public interface LoanApplicationDao {
    void addLoanApplication(LoanApplication application);

    LoanApplication getLoanApplicationById(int applicationId);
    List<LoanApplication> getAllApplications();

    void updateLoanApplication(LoanApplication application);

    void deleteLoanApplication(int applicationId);
    boolean existsByLoanTypeId(int loanTypeId);
}
