package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.model.Loan;
import com.loanmanagement.service.LoanService;

public class LoanServiceImpl implements LoanService {

    private LoanDao loanDao = new LoanDaoImpl();

    @Override
    public void addLoan(Loan loan) {
        loanDao.addLoan(loan);
    }

    @Override
    public Loan getLoanById(int loanId) {
        return loanDao.getLoanById(loanId);
    }

    @Override
    public void updateLoan(Loan loan) {
        loanDao.updateLoan(loan);
    }

    @Override
    public void deleteLoan(int loanId) {
        loanDao.deleteLoan(loanId);
    }
}