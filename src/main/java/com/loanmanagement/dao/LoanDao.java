package com.loanmanagement.dao;

import com.loanmanagement.model.Loan;

import java.util.List;

public interface LoanDao {
    void addLoan(Loan loan);

    Loan getLoanById(int loanId);
    Loan getLoanByApplicationId(int applicationId);
    List<Loan> getAllLoans();
    void updateLoan(Loan loan);

    void deleteLoan(int loanId);
}
