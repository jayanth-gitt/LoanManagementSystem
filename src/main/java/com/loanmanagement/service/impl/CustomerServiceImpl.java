package com.loanmanagement.service.impl;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.exception.ValidationException;
import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.util.ValidationUtil;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = new CustomerDaoImpl();

    @Override
    public void addCustomer(Customer customer) {
        if (customer.getFullName() == null ||
                customer.getFullName().trim().isEmpty()) {
            throw new ValidationException(
                    "Customer name is required");
        }

        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            throw new ValidationException(
                    "Invalid email format");
        }

        if (!ValidationUtil.isValidPhone(customer.getPhone())) {
            throw new ValidationException(
                    "Invalid phone number");
        }

        customerDao.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(int customerId) {
        return customerDao.getCustomerById(customerId);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getFullName() == null ||
                customer.getFullName().trim().isEmpty()) {
            throw new ValidationException(
                    "Customer name is required");
        }

        if (!ValidationUtil.isValidEmail(customer.getEmail())) {
            throw new ValidationException(
                    "Invalid email format");
        }

        if (!ValidationUtil.isValidPhone(customer.getPhone())) {
            throw new ValidationException(
                    "Invalid phone number");
        }

        customerDao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerDao.deleteCustomer(customerId);
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }
    @Override
    public Customer getCustomerByUsername(String username) {
        return customerDao.getCustomerByUsername(username);
    }
}