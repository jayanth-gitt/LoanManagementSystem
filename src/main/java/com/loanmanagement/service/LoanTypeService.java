package com.loanmanagement.service;

import com.loanmanagement.model.LoanType;

public interface LoanTypeService {
    int addLoanType(LoanType loanType);

    LoanType getLoanTypeById(int loanTypeId);

    int updateLoanType(LoanType loanType);

    int deleteLoanType(int loanTypeId);
}
