package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.UserDao;

import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.dao.impl.UserDaoImpl;

import com.loanmanagement.exception.BusinessException;
import com.loanmanagement.exception.NotFoundException;
import com.loanmanagement.exception.ValidationException;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;

import com.loanmanagement.service.LoanApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanApplicationServiceImpl
        implements LoanApplicationService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    LoanApplicationServiceImpl.class
            );


    // =========================================================
    // DAOs
    // =========================================================

    private final LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();

    private final LoanTypeDao loanTypeDao =
            new LoanTypeDaoImpl();

    private final UserDao userDao =
            new UserDaoImpl();

    private final CustomerDao customerDao =
            new CustomerDaoImpl();


    // =========================================================
    // ADD / APPLY FOR LOAN
    // =========================================================

    @Override
    public int addApplication(
            LoanApplication application) {

        if (application == null) {
            throw new ValidationException(
                    "Loan application cannot be null"
            );
        }


        // -----------------------------------------------------
        // Validate customer
        // -----------------------------------------------------

        Customer customer =
                customerDao.getCustomerById(
                        application.getCustomerId()
                );

        if (customer == null) {
            throw new NotFoundException(
                    "Customer not found"
            );
        }


        // -----------------------------------------------------
        // Customer must be active
        // -----------------------------------------------------

        if (!"ACTIVE".equals(
                customer.getStatus().name())) {

            throw new BusinessException(
                    "Inactive customer cannot apply for a loan"
            );
        }


        // -----------------------------------------------------
        // KYC must be VERIFIED
        // -----------------------------------------------------

        if (!"VERIFIED".equals(
                customer.getKycStatus().name())) {

            throw new BusinessException(
                    "KYC must be VERIFIED before applying for a loan"
            );
        }


        // -----------------------------------------------------
        // Get loan type
        // -----------------------------------------------------

        LoanType loanType =
                loanTypeDao.getLoanTypeById(
                        application.getLoanTypeId()
                );

        if (loanType == null) {

            throw new NotFoundException(
                    "Loan type not found"
            );
        }


        // -----------------------------------------------------
        // Loan type must be ACTIVE
        // -----------------------------------------------------

        if (!"ACTIVE".equals(
                loanType.getStatus())) {

            throw new BusinessException(
                    "Loan type is not available"
            );
        }


        // -----------------------------------------------------
        // Validate requested amount
        // -----------------------------------------------------
//        System.out.println("Requested Amount: " + application.getRequestedAmount());
//        System.out.println("Min Amount: " + loanType.getMinAmount());
//        System.out.println("Max Amount: " + loanType.getMaxAmount());
//        System.out.println("Tenure: " + application.getTenureMonths());
        if (application.getRequestedAmount()
                < loanType.getMinAmount()
                ||
                application.getRequestedAmount()
                        > loanType.getMaxAmount()) {

            throw new ValidationException(
                    "Requested amount is outside the allowed loan amount range"
            );
        }


        // -----------------------------------------------------
        // Validate tenure
        // -----------------------------------------------------

        if (application.getTenureMonths() <= 0
                ||
                application.getTenureMonths()
                        > loanType.getMaxTenureMonths()) {

            throw new ValidationException(
                    "Invalid loan tenure"
            );
        }


        // -----------------------------------------------------
        // Every new application starts as PENDING
        // -----------------------------------------------------

        application.setStatus("PENDING");

        application.setRemarks(null);

        application.setReviewedBy(0);

        application.setReviewedAt(null);


        // -----------------------------------------------------
        // Insert application
        // -----------------------------------------------------

        int rowsAffected =
                loanApplicationDao.addLoanApplication(
                        application
                );

        logger.info(
                "Loan application created for customer ID: {}. Rows affected: {}",
                application.getCustomerId(),
                rowsAffected
        );

        return rowsAffected;
    }


    // =========================================================
    // GET APPLICATION
    // =========================================================

    @Override
    public LoanApplication getApplicationById(
            int applicationId) {

        LoanApplication application =
                loanApplicationDao
                        .getLoanApplicationById(
                                applicationId
                        );

        if (application == null) {

            throw new NotFoundException(
                    "Application not found"
            );
        }

        return application;
    }


    // =========================================================
    // UPDATE APPLICATION
    // =========================================================

    @Override
    public int updateApplication(
            LoanApplication application) {

        if (application == null) {
            throw new ValidationException(
                    "Loan application cannot be null"
            );
        }


        LoanApplication existingApplication =
                loanApplicationDao
                        .getLoanApplicationById(
                                application.getApplicationId()
                        );

        if (existingApplication == null) {

            throw new NotFoundException(
                    "Application not found"
            );
        }


        // -----------------------------------------------------
        // Only PENDING applications can be updated
        // -----------------------------------------------------

        if (!"PENDING".equals(
                existingApplication.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be updated"
            );
        }


        // -----------------------------------------------------
        // Keep application PENDING
        // -----------------------------------------------------

        application.setStatus("PENDING");


        // Do not allow normal update to change review details
        application.setReviewedBy(
                existingApplication.getReviewedBy()
        );

        application.setReviewedAt(
                existingApplication.getReviewedAt()
        );


        int rowsAffected =
                loanApplicationDao.updateLoanApplication(
                        application
                );

        logger.info(
                "Loan application {} updated. Rows affected: {}",
                application.getApplicationId(),
                rowsAffected
        );

        return rowsAffected;
    }


    // =========================================================
    // APPROVE APPLICATION
    // =========================================================

    @Override
    public int approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {


        // -----------------------------------------------------
        // Check application
        // -----------------------------------------------------

        LoanApplication application =
                loanApplicationDao
                        .getLoanApplicationById(
                                applicationId
                        );

        if (application == null) {

            throw new NotFoundException(
                    "Application not found"
            );
        }


        // -----------------------------------------------------
        // Only PENDING applications can be approved
        // -----------------------------------------------------

        if (!"PENDING".equals(
                application.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be approved"
            );
        }


        // -----------------------------------------------------
        // Check Loan Officer
        // -----------------------------------------------------

        User loanOfficer =
                userDao.getUserById(
                        loanOfficerId
                );

        if (loanOfficer == null) {

            throw new NotFoundException(
                    "Loan Officer not found"
            );
        }


        // -----------------------------------------------------
        // Check role
        // -----------------------------------------------------

        if (!"LOAN_OFFICER".equals(
                loanOfficer.getRole())) {

            throw new BusinessException(
                    "Only Loan Officer can approve application"
            );
        }


        // -----------------------------------------------------
        // Check officer status
        // -----------------------------------------------------

        if (!"ACTIVE".equals(
                loanOfficer.getStatus())) {

            throw new BusinessException(
                    "Loan Officer is inactive"
            );
        }


        // -----------------------------------------------------
        // Approve using DAO workflow method
        // -----------------------------------------------------

        int rowsAffected =
                loanApplicationDao.approveApplication(
                        applicationId,
                        loanOfficerId,
                        remarks
                );


        logger.info(
                "Loan application {} approved by Loan Officer {}. Rows affected: {}",
                applicationId,
                loanOfficerId,
                rowsAffected
        );

        return rowsAffected;
    }


    // =========================================================
    // REJECT APPLICATION
    // =========================================================

    @Override
    public int rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {


        // -----------------------------------------------------
        // Rejection remarks are mandatory
        // -----------------------------------------------------

        if (remarks == null
                || remarks.trim().isEmpty()) {

            throw new ValidationException(
                    "Rejection remarks are required"
            );
        }


        // -----------------------------------------------------
        // Check application
        // -----------------------------------------------------

        LoanApplication application =
                loanApplicationDao
                        .getLoanApplicationById(
                                applicationId
                        );

        if (application == null) {

            throw new NotFoundException(
                    "Application not found"
            );
        }


        // -----------------------------------------------------
        // Only PENDING applications can be rejected
        // -----------------------------------------------------

        if (!"PENDING".equals(
                application.getStatus())) {

            throw new BusinessException(
                    "Only pending applications can be rejected"
            );
        }


        // -----------------------------------------------------
        // Check Loan Officer
        // -----------------------------------------------------

        User loanOfficer =
                userDao.getUserById(
                        loanOfficerId
                );

        if (loanOfficer == null) {

            throw new NotFoundException(
                    "Loan Officer not found"
            );
        }


        // -----------------------------------------------------
        // Check role
        // -----------------------------------------------------

        if (!"LOAN_OFFICER".equals(
                loanOfficer.getRole())) {

            throw new BusinessException(
                    "Only Loan Officer can reject application"
            );
        }


        // -----------------------------------------------------
        // Check officer status
        // -----------------------------------------------------

        if (!"ACTIVE".equals(
                loanOfficer.getStatus())) {

            throw new BusinessException(
                    "Loan Officer is inactive"
            );
        }


        // -----------------------------------------------------
        // Reject using DAO workflow method
        // -----------------------------------------------------

        int rowsAffected =
                loanApplicationDao.rejectApplication(
                        applicationId,
                        loanOfficerId,
                        remarks
                );


        logger.info(
                "Loan application {} rejected by Loan Officer {}. Rows affected: {}",
                applicationId,
                loanOfficerId,
                rowsAffected
        );

        return rowsAffected;
    }


    // =========================================================
    // DELETE APPLICATION
    // =========================================================

    @Override
    public int deleteApplication(
            int applicationId) {

        LoanApplication application =
                loanApplicationDao
                        .getLoanApplicationById(
                                applicationId
                        );

        if (application == null) {

            throw new NotFoundException(
                    "Application not found"
            );
        }


        int rowsAffected =
                loanApplicationDao
                        .deleteLoanApplication(
                                applicationId
                        );

        logger.info(
                "Loan application {} deleted. Rows affected: {}",
                applicationId,
                rowsAffected
        );

        return rowsAffected;
    }
}