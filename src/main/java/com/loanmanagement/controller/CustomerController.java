package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.impl.CustomerServiceImpl;

import java.util.List;

public class CustomerController {
    private final CustomerService customerService =
            new CustomerServiceImpl();

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
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();

    }
    public Customer getCustomerByUsername(String username) {
        return customerService.getCustomerByUsername(username);
    }
}
