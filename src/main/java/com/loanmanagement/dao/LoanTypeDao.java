package com.loanmanagement.dao;

import com.loanmanagement.model.LoanType;

public interface LoanTypeDao {
    int addLoanType(LoanType loanType);

    LoanType getLoanTypeById(int loanTypeId);

    int updateLoanType(LoanType loanType);

    int deleteLoanType(int loanTypeId);
}
