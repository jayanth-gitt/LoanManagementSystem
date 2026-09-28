package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.exception.BusinessException;
import com.loanmanagement.exception.NotFoundException;
import com.loanmanagement.exception.ValidationException;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;
import com.loanmanagement.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoanServiceImpl implements LoanService {
    private static final Logger logger =
            LoggerFactory.getLogger(LoanServiceImpl.class);
    private LoanDao loanDao = new LoanDaoImpl();
    private LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();
    private LoanTypeDao loanTypeDao=new LoanTypeDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void addLoan(Loan loan) {

        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(
                        loan.getApplicationId());

        if (application == null) {
            throw new NotFoundException(
                    "Loan application not found");
        }

        if (!"APPROVED".equals(application.getStatus())) {
            throw new BusinessException(
                    "Loan can be created only from an approved application");
        }
        Loan existingLoan =
                loanDao.getLoanByApplicationId(loan.getApplicationId());

        if (existingLoan != null) {
            throw new BusinessException(
                    "A loan already exists for this application");
        }
        User loanOfficer = userDao.getUserById(loan.getCreatedBy());

        if (loanOfficer == null) {
            throw new NotFoundException("Loan Officer not found");
        }

        if (!"LOAN_OFFICER".equals(loanOfficer.getRole())) {
            throw new BusinessException(
                    "Only Loan Officer can create a loan");
        }
        if (!"ACTIVE".equals(loanOfficer.getStatus())) {
            throw new BusinessException("Loan Officer is inactive");
        }

        LoanType loanType =
                loanTypeDao.getLoanTypeById(application.getLoanTypeId());

        if (loanType == null) {
            throw new NotFoundException("Loan type not found");
        }
        loan.setCustomerId(application.getCustomerId());
        loan.setLoanTypeId(application.getLoanTypeId());
        loan.setPrincipalAmount(application.getRequestedAmount());
        loan.setTenureMonths(application.getTenureMonths());
        loan.setInterestRate(loanType.getInterestRate());
        loan.setTotalPayable(loan.getPrincipalAmount());
        loan.setOutstandingAmount(loan.getPrincipalAmount());
        loan.setStatus("ACTIVE");

        loanDao.addLoan(loan);

        logger.info("Loan added successfully");
    }

    @Override
    public Loan getLoanById(int loanId) {

        return loanDao.getLoanById(loanId);
    }

    @Override
    public void updateLoan(Loan loan) {

        if (loan.getPrincipalAmount() <= 0) {
            throw new ValidationException(
                    "Principal amount must be greater than zero");
        }

        if (loan.getOutstandingAmount() < 0) {
            throw new ValidationException(
                    "Outstanding amount cannot be negative");
        }

        if (loan.getOutstandingAmount() > loan.getPrincipalAmount()) {
            throw new ValidationException(
                    "Outstanding amount cannot exceed principal amount");
        }

        if (loan.getTenureMonths() <= 0) {
            throw new ValidationException(
                    "Tenure must be greater than zero");
        }
        if (loan.getStatus() == null ||
                (!"ACTIVE".equals(loan.getStatus())
                        && !"CLOSED".equals(loan.getStatus()))) {
            throw new ValidationException("Invalid loan status");
        }

        loanDao.updateLoan(loan);
    }

    @Override
    public void deleteLoan(int loanId) {
        loanDao.deleteLoan(loanId);
    }
    @Override
    public List<Loan> getAllLoans() {
        return loanDao.getAllLoans();
    }
}

