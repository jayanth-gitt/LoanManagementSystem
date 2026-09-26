package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;

import com.loanmanagement.service.AuthService;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.LoanApplicationService;
import com.loanmanagement.service.LoanService;
import com.loanmanagement.service.LoanTypeService;
import com.loanmanagement.service.UserService;

import com.loanmanagement.service.impl.AuthServiceImpl;
import com.loanmanagement.service.impl.CustomerServiceImpl;
import com.loanmanagement.service.impl.LoanApplicationServiceImpl;
import com.loanmanagement.service.impl.LoanServiceImpl;
import com.loanmanagement.service.impl.LoanTypeServiceImpl;
import com.loanmanagement.service.impl.UserServiceImpl;

public class AppController {

    private final UserService userService =
            new UserServiceImpl();

    private final LoanTypeService loanTypeService =
            new LoanTypeServiceImpl();

    private final CustomerService customerService =
            new CustomerServiceImpl();

    private final LoanApplicationService loanApplicationService =
            new LoanApplicationServiceImpl();

    private final LoanService loanService =
            new LoanServiceImpl();

    private final AuthService authService =
            new AuthServiceImpl();


    // =========================
    // AUTHENTICATION
    // =========================

    public boolean login(String username, String password) {
        return authService.login(username, password);
    }

    public void logout(int userId) {
        authService.logout(userId);
    }


    // =========================
    // USER MANAGEMENT
    // =========================

    public void addUser(User user) {
        userService.addUser(user);
    }

    public User getUserById(int userId) {
        return userService.getUserById(userId);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public void deleteUser(int userId) {
        userService.deleteUser(userId);
    }


    // =========================
    // LOAN TYPE MANAGEMENT
    // =========================

    public void addLoanType(LoanType loanType) {
        loanTypeService.addLoanType(loanType);
    }

    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeService.getLoanTypeById(loanTypeId);
    }

    public void updateLoanType(LoanType loanType) {
        loanTypeService.updateLoanType(loanType);
    }

    public void deleteLoanType(int loanTypeId) {
        loanTypeService.deleteLoanType(loanTypeId);
    }


    // =========================
    // CUSTOMER MANAGEMENT
    // =========================

    public void addCustomer(Customer customer) {
        customerService.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerService.getCustomerById(customerId);
    }

    public void updateCustomer(Customer customer) {
        customerService.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId) {
        customerService.deleteCustomer(customerId);
    }


    // =========================
    // LOAN APPLICATION
    // =========================

    public void addApplication(LoanApplication application) {
        loanApplicationService.addApplication(application);
    }

    public LoanApplication getApplicationById(int applicationId) {
        return loanApplicationService
                .getApplicationById(applicationId);
    }

    public void approveApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        loanApplicationService.approveApplication(
                applicationId,
                loanOfficerId,
                remarks
        );
    }

    public void rejectApplication(
            int applicationId,
            int loanOfficerId,
            String remarks) {

        loanApplicationService.rejectApplication(
                applicationId,
                loanOfficerId,
                remarks
        );
    }

    public void updateApplication(
            LoanApplication application) {

        loanApplicationService
                .updateApplication(application);
    }

    public void deleteApplication(int applicationId) {
        loanApplicationService
                .deleteApplication(applicationId);
    }


    // =========================
    // LOAN MANAGEMENT
    // =========================

    public void addLoan(Loan loan) {
        loanService.addLoan(loan);
    }

    public Loan getLoanById(int loanId) {
        return loanService.getLoanById(loanId);
    }

    public void updateLoan(Loan loan) {
        loanService.updateLoan(loan);
    }

    public void deleteLoan(int loanId) {
        loanService.deleteLoan(loanId);
    }


    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        AppController controller =
                new AppController();

        System.out.println(
                "Loan Management System started successfully."
        );
    }
}