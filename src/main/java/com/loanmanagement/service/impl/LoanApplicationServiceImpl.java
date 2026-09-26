package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.UserDao;

import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.dao.impl.UserDaoImpl;

import com.loanmanagement.exception.BusinessException;
import com.loanmanagement.exception.NotFoundException;
import com.loanmanagement.exception.ValidationException;

import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;

import com.loanmanagement.service.LoanApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanApplicationServiceImpl
        implements LoanApplicationService {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanApplicationServiceImpl.class);

    private final LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();

    private final LoanTypeDao loanTypeDao =
            new LoanTypeDaoImpl();

    private final UserDao userDao =
            new UserDaoImpl();

    @Override
    public void addApplication(LoanApplication application) {

        LoanType loanType =
                loanTypeDao.getLoanTypeById(
                        application.getLoanTypeId()
                );

        if (loanType == null) {
            throw new NotFoundException(
                    "Loan type not found"
            );
        }

        if (!"ACTIVE".equals(loanType.getStatus())) {
            throw new BusinessException(
                    "Loan type is not available"
            );
        }

        if (application.getRequestedAmount()
                < loanType.getMinAmount()
                ||
                application.getRequestedAmount()
                        > loanType.getMaxAmount()) {

            throw new ValidationException(
                    "Requested amount is outside the allowed loan amount range"
            );
        }

        if (application.getTenureMonths() <= 0
                ||
                application.getTenureMonths()
                        > loanType.getMaxTenureMonths()) {

            throw new ValidationException(
                    "Invalid loan tenure"
            );
        }

        application.setStatus("PENDING");

        loanApplicationDao.addLoanApplication(application);

        logger.info(
                "Loan application added successfully"
        );
    }

    @Override
    public LoanApplication getApplicationById(
            int applicationId) {

        return loanApplicationDao
                .getLoanApplicationById(applicationId);
    }

    @Override
    public void updateApplication(
            LoanApplication application) {

        LoanApplication existingApplication =
                loanApplicationDao.getLoanApplicationById(
                        application.getApplicationId()
                );

        if (existingApplication == null) {
            throw new NotFoundException(
                    "Application not found"
            );
        }

        if (!"PENDING".equals(
                existingApplication.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be updated"
            );
        }

        // Keep application status as PENDING
        application.setStatus(
                existingApplication.getStatus()
        );

        loanApplicationDao.updateLoanApplication(
                application
        );

        logger.info(
                "Loan application updated successfully"
        );
    }

    @Override
    public void approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(
                        applicationId
                );

        if (application == null) {
            throw new NotFoundException(
                    "Application not found"
            );
        }

        if (!"PENDING".equals(
                application.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be approved"
            );
        }

        User loanOfficer =
                userDao.getUserById(loanOfficerId);

        if (loanOfficer == null) {
            throw new NotFoundException(
                    "Loan Officer not found"
            );
        }

        if (!"LOAN_OFFICER".equals(
                loanOfficer.getRole())) {

            throw new BusinessException(
                    "Only Loan Officer can approve application"
            );
        }

        if (!"ACTIVE".equals(
                loanOfficer.getStatus())) {

            throw new BusinessException(
                    "Loan Officer is inactive"
            );
        }

        application.setStatus("APPROVED");

        application.setReviewedBy(
                loanOfficerId
        );

        application.setRemarks(
                remarks
        );

        application.setReviewedAt(
                java.time.LocalDateTime.now().toString()
        );

        loanApplicationDao.updateLoanApplication(
                application
        );

        logger.info(
                "Loan application approved successfully"
        );
    }

    @Override
    public void rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(
                        applicationId
                );

        if (application == null) {
            throw new NotFoundException(
                    "Application not found"
            );
        }

        if (!"PENDING".equals(
                application.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be rejected"
            );
        }

        User loanOfficer =
                userDao.getUserById(loanOfficerId);

        if (loanOfficer == null) {
            throw new NotFoundException(
                    "Loan Officer not found"
            );
        }

        if (!"LOAN_OFFICER".equals(
                loanOfficer.getRole())) {

            throw new BusinessException(
                    "Only Loan Officer can reject application"
            );
        }

        if (!"ACTIVE".equals(
                loanOfficer.getStatus())) {

            throw new BusinessException(
                    "Loan Officer is inactive"
            );
        }

        if (remarks == null
                || remarks.trim().isEmpty()) {

            throw new ValidationException(
                    "Rejection remarks are required"
            );
        }

        application.setStatus("REJECTED");

        application.setReviewedBy(
                loanOfficerId
        );

        application.setRemarks(
                remarks
        );

        application.setReviewedAt(
                java.time.LocalDateTime.now().toString()
        );

        loanApplicationDao.updateLoanApplication(
                application
        );

        logger.info(
                "Loan application rejected successfully"
        );
    }

    @Override
    public void deleteApplication(
            int applicationId) {

        loanApplicationDao.deleteLoanApplication(
                applicationId
        );
    }
}