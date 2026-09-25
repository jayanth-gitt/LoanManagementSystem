package com.loanmanagement.service;

import com.loanmanagement.model.Customer;

public interface CustomerService {

    int addCustomer(Customer customer);

    Customer getCustomerById(int customerId);

    int updateCustomer(Customer customer);

    int deleteCustomer(int customerId);
}