package com.loanmanagement;

import com.loanmanagement.dao.CustomerDao;
import com.loanmanagement.dao.impl.CustomerDaoImpl;
import com.loanmanagement.model.Customer;
import org.junit.jupiter.api.Test;

public class CustomerDaoImplTest {
    private CustomerDao customerDao = new CustomerDaoImpl();
    @Test
    public void testAddCustomer() {

        Customer customer = new Customer();

        customer.setUserId(19);
        customer.setFullName("Applecustomer");
        customer.setEmail("junit.customer@gmail.com");
        customer.setPhone("9876543210");
        customer.setDob("2000-01-01");
        customer.setAddress("Hyderabad");
        customer.setMonthlyIncome(50000);
        customer.setPanNumber("ABCDE1234F");
        customer.setAadhaarLast4("1234");
        customer.setEmploymentType("SALARIED");
        customer.setAccountNumber("1234567890");
        customer.setIfscCode("SBIN0001234");
        customer.setBankName("SBI");
        customer.setKycStatus("PENDING");
        customer.setKycVerifiedBy(19);
        customer.setStatus("ACTIVE");
        customerDao.addCustomer(customer);
    }
    @Test
    public void testGetCustomerById() {

        Customer customer = customerDao.getCustomerById(20);

        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getFullName());
        System.out.println("Email: " + customer.getEmail());
    }
    @Test
    public void testUpdateCustomer() {

        Customer customer = customerDao.getCustomerById(20);

        customer.setAddress("Hyderabad");
        customer.setMonthlyIncome(60000);

        customerDao.updateCustomer(customer);
    }
    @Test
    public void testDeleteCustomer() {

        customerDao.deleteCustomer(20);
    }
}
