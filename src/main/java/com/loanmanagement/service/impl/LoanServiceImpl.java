package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.model.Loan;
import com.loanmanagement.service.LoanService;

public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao = new LoanDaoImpl();

    @Override
    public int addLoan(Loan loan) {
        return loanDao.addLoan(loan);
    }

    @Override
    public Loan getLoanById(int loanId) {
        return loanDao.getLoanById(loanId);
    }

    @Override
    public int updateLoan(Loan loan) {
        return loanDao.updateLoan(loan);
    }

    @Override
    public int deleteLoan(int loanId) {
        return loanDao.deleteLoan(loanId);
    }
}