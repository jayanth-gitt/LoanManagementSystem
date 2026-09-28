package com.loanmanagement.controller;

import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;

import java.util.List;

public class LoanTypeController {
    private final LoanTypeService loanTypeService =
            new LoanTypeServiceImpl();

    public void addLoanType(LoanType loanType) {
        loanTypeService.addLoanType(loanType);
    }

    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeService.getLoanTypeById(loanTypeId);
    }
    public List<LoanType> getAllLoanTypes() {
        return loanTypeService.getAllLoanTypes();
    }

    public void updateLoanType(LoanType loanType) {
        loanTypeService.updateLoanType(loanType);
    }

    public void deleteLoanType(int loanTypeId) {
        loanTypeService.deleteLoanType(loanTypeId);
    }
}
