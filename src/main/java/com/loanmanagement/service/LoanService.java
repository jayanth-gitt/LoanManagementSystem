package com.loanmanagement.service;

import com.loanmanagement.model.Loan;

public interface LoanService {
    int addLoan(Loan loan);

    Loan getLoanById(int loanId);

    int updateLoan(Loan loan);

    int deleteLoan(int loanId);
}
