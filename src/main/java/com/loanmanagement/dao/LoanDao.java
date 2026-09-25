package com.loanmanagement.dao;

import com.loanmanagement.model.Loan;

public interface LoanDao {
    int addLoan(Loan loan);

    Loan getLoanById(int loanId);

    int updateLoan(Loan loan);

    int deleteLoan(int loanId);
}
