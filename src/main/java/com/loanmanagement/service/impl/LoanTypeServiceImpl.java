package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;

public class LoanTypeServiceImpl implements LoanTypeService {

    private LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();

    @Override
    public void addLoanType(LoanType loanType) {
        loanTypeDao.addLoanType(loanType);
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeDao.getLoanTypeById(loanTypeId);
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        loanTypeDao.updateLoanType(loanType);
    }

    @Override
    public void deleteLoanType(int loanTypeId) {
        loanTypeDao.deleteLoanType(loanTypeId);
    }
}