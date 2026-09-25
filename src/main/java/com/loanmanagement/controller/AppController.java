package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;

import com.loanmanagement.service.ApplicationService;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.UserService;

import com.loanmanagement.service.impl.ApplicationServiceImpl;
import com.loanmanagement.service.impl.CustomerServiceImpl;
import com.loanmanagement.service.impl.LoanServiceImpl;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import com.loanmanagement.service.impl.UserServiceImpl;

public class AppController {

    private final UserService userService;
    private final CustomerService customerService;
    private final LoanTypeService loanTypeService;
    private final ApplicationService applicationService;
    private final LoanService loanService;

    public AppController() {

        userService = new UserServiceImpl();
        customerService = new CustomerServiceImpl();
        loanTypeService = new LoanTypeServiceImpl();
        applicationService = new ApplicationServiceImpl();
        loanService = new LoanServiceImpl();
    }


    // =========================
    // USER
    // =========================

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


    // =========================
    // CUSTOMER
    // =========================

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


    // =========================
    // LOAN TYPE
    // =========================

    public int addLoanType(LoanType loanType) {
        return loanTypeService.addLoanType(loanType);
    }

    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeService.getLoanTypeById(loanTypeId);
    }

    public int updateLoanType(LoanType loanType) {
        return loanTypeService.updateLoanType(loanType);
    }

    public int deleteLoanType(int loanTypeId) {
        return loanTypeService.deleteLoanType(loanTypeId);
    }


    // =========================
    // LOAN APPLICATION
    // =========================

    public int addLoanApplication(LoanApplication application) {
        return applicationService.addApplication(application);
    }

    public LoanApplication getLoanApplicationById(int applicationId) {
        return applicationService.getApplicationById(applicationId);
    }

    public int updateLoanApplication(LoanApplication application) {
        return applicationService.updateApplication(application);
    }

    public int deleteLoanApplication(int applicationId) {
        return applicationService.deleteApplication(applicationId);
    }


    // =========================
    // LOAN
    // =========================

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