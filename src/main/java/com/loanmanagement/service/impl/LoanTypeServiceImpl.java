package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;

public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();

    @Override
    public int addLoanType(LoanType loanType) {
        return loanTypeDao.addLoanType(loanType);
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeDao.getLoanTypeById(loanTypeId);
    }

    @Override
    public int updateLoanType(LoanType loanType) {
        return loanTypeDao.updateLoanType(loanType);
    }

    @Override
    public int deleteLoanType(int loanTypeId) {
        return loanTypeDao.deleteLoanType(loanTypeId);
    }
}