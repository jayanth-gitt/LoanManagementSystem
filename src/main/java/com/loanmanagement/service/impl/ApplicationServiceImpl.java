package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {

    private final LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();

    @Override
    public int addApplication(LoanApplication application) {

        return loanApplicationDao.addLoanApplication(application);
    }

    @Override
    public LoanApplication getApplicationById(int applicationId) {

        return loanApplicationDao.getLoanApplicationById(applicationId);
    }

    @Override
    public int updateApplication(LoanApplication application) {

        return loanApplicationDao.updateLoanApplication(application);
    }

    @Override
    public int deleteApplication(int applicationId) {

        return loanApplicationDao.deleteLoanApplication(applicationId);
    }
}