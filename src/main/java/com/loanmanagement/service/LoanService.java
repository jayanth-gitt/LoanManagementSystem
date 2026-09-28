package com.loanmanagement.service;

import com.loanmanagement.model.Loan;

import java.util.List;

public interface LoanService {
    void addLoan(Loan loan);

    Loan getLoanById(int loanId);
    List<Loan> getAllLoans();

    void updateLoan(Loan loan);

    void deleteLoan(int loanId);
}
