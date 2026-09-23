package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {

    private LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();

    @Override
    public void addApplication(LoanApplication application) {
        loanApplicationDao.addLoanApplication(application);
    }

    @Override
    public LoanApplication getApplicationById(int applicationId) {
        return loanApplicationDao.getLoanApplicationById(applicationId);
    }

    @Override
    public void updateApplication(LoanApplication application) {
        loanApplicationDao.updateLoanApplication(application);
    }

    @Override
    public void deleteApplication(int applicationId) {
        loanApplicationDao.deleteLoanApplication(applicationId);
    }
}