package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;

import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.LoanApplicationService;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.UserService;

import com.loanmanagement.service.impl.CustomerServiceImpl;
import com.loanmanagement.service.impl.LoanApplicationServiceImpl;
import com.loanmanagement.service.impl.LoanServiceImpl;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import com.loanmanagement.service.impl.UserServiceImpl;

public class AppController {

    // =========================================================
    // SERVICES
    // =========================================================

    private final UserService userService;
    private final CustomerService customerService;
    private final LoanTypeService loanTypeService;
    private final LoanApplicationService loanApplicationService;
    private final LoanService loanService;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AppController() {

        userService = new UserServiceImpl();

        customerService = new CustomerServiceImpl();

        loanTypeService = new LoanTypeServiceImpl();

        loanApplicationService =
                new LoanApplicationServiceImpl();

        loanService = new LoanServiceImpl();
    }


    // =========================================================
    // USER MODULE
    // =========================================================

    public int addUser(User user) {

        return userService.addUser(user);
    }

    public User getUserById(int userId) {

        return userService.getUserById(userId);
    }

    public int updateUser(User user) {

        return userService.updateUser(user);
    }

    public int deleteUser(int userId) {

        return userService.deleteUser(userId);
    }


    // =========================================================
    // CUSTOMER MODULE
    // =========================================================

    public int addCustomer(Customer customer) {

        return customerService.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {

        return customerService.getCustomerById(customerId);
    }

    public int updateCustomer(Customer customer) {

        return customerService.updateCustomer(customer);
    }

    public int deleteCustomer(int customerId) {

        return customerService.deleteCustomer(customerId);
    }


    // =========================================================
    // KYC MODULE
    // =========================================================

    public int verifyCustomerKyc(
            int customerId,
            int verifiedBy) {

        return customerService.verifyKyc(
                customerId,
                verifiedBy
        );
    }


    public int rejectCustomerKyc(
            int customerId,
            int verifiedBy,
            String remarks) {

        return customerService.rejectKyc(
                customerId,
                verifiedBy,
                remarks
        );
    }


    // =========================================================
    // LOAN TYPE MODULE
    // =========================================================

    public int addLoanType(LoanType loanType) {

        return loanTypeService.addLoanType(loanType);
    }

    public LoanType getLoanTypeById(
            int loanTypeId) {

        return loanTypeService.getLoanTypeById(
                loanTypeId
        );
    }

    public int updateLoanType(
            LoanType loanType) {

        return loanTypeService.updateLoanType(
                loanType
        );
    }

    public int deleteLoanType(
            int loanTypeId) {

        return loanTypeService.deleteLoanType(
                loanTypeId
        );
    }


    // =========================================================
    // LOAN APPLICATION MODULE
    // =========================================================

    public int addLoanApplication(
            LoanApplication application) {

        return loanApplicationService.addApplication(
                application
        );
    }


    public LoanApplication getLoanApplicationById(
            int applicationId) {

        return loanApplicationService
                .getApplicationById(
                        applicationId
                );
    }


    public int updateLoanApplication(
            LoanApplication application) {

        return loanApplicationService
                .updateApplication(
                        application
                );
    }


    public int approveLoanApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        return loanApplicationService
                .approveApplication(
                        applicationId,
                        loanOfficerId,
                        remarks
                );
    }


    public int rejectLoanApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        return loanApplicationService
                .rejectApplication(
                        applicationId,
                        loanOfficerId,
                        remarks
                );
    }


    public int deleteLoanApplication(
            int applicationId) {

        return loanApplicationService
                .deleteApplication(
                        applicationId
                );
    }


    // =========================================================
    // LOAN MODULE
    // =========================================================

    public int addLoan(Loan loan) {

        return loanService.addLoan(loan);
    }

    public Loan getLoanById(int loanId) {

        return loanService.getLoanById(loanId);
    }

    public int updateLoan(Loan loan) {

        return loanService.updateLoan(loan);
    }

    public int deleteLoan(int loanId) {

        return loanService.deleteLoan(loanId);
    }
}