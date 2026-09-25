package com.loanmanagement.dao;

import com.loanmanagement.model.Customer;

public interface CustomerDao {

    int addCustomer(Customer customer);

    Customer getCustomerById(int customerId);

    int updateCustomer(Customer customer);

    int deleteCustomer(int customerId);
}