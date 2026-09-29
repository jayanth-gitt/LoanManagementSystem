package com.loanmanagement;

import com.loanmanagement.model.Customer;
import com.loanmanagement.service.CustomerService;
import com.loanmanagement.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerServiceImplTest {
    private CustomerService customerService =
            new CustomerServiceImpl();

    @Test
    public void testAddCustomer() {

        Customer customer = new Customer();

        customer.setUserId(1);
        customer.setFullName("Service Test Customer");
        customer.setEmail("service.customer@gmail.com");
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
        customer.setKycVerifiedBy(1);
        customer.setCreditScore(750);
        customer.setExistingEmi(5000);
        customer.setStatus("ACTIVE");

        customerService.addCustomer(customer);
    }

    @Test
    public void testGetCustomerById() {

        Customer customer = customerService.getCustomerById(1);

        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getFullName());
        System.out.println("Email: " + customer.getEmail());
    }
    @Test
    public void testUpdateCustomer() {

        Customer customer = customerService.getCustomerById(1);

        customer.setAddress("Updated Hyderabad");
        customer.setMonthlyIncome(60000);

        customerService.updateCustomer(customer);
    }
    @Test
    public void testDeleteCustomer() {

        customerService.deleteCustomer(999);
    }
    @Test
    public void testAddCustomerWithInvalidEmail() {

        Customer customer = new Customer();

        customer.setFullName("Invalid Email Customer");
        customer.setEmail("invalid-email");
        customer.setPhone("9876543210");

        assertThrows(IllegalArgumentException.class, () -> {
            customerService.addCustomer(customer);
        });
    }

    @Test
    public void testAddCustomerWithInvalidPhone() {

        Customer customer = new Customer();

        customer.setFullName("Invalid Phone Customer");
        customer.setEmail("test@gmail.com");
        customer.setPhone("12345");

        assertThrows(IllegalArgumentException.class, () -> {
            customerService.addCustomer(customer);
        });
    }

}