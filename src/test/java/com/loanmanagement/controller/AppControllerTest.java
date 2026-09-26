package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.CustomerStatus;
import com.loanmanagement.model.KycStatus;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    private AppController appController;

    private String testUsername;
    private String testEmail;
    private String testLoanTypeName;

    private int testUserId;
    private int testCustomerId;
    private int testLoanTypeId;
    private int testApplicationId;
    private int testLoanId;


    // ============================================================
    // SETUP
    // ============================================================

    @BeforeEach
    void setUp() {

        appController = new AppController();

        String uniqueId =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);

        testUsername = "junit_user_" + uniqueId;
        testEmail = "junit_" + uniqueId + "@test.com";
        testLoanTypeName = "JUnit Loan " + uniqueId;
    }


    // ============================================================
    // CLEANUP
    // ============================================================

    @AfterEach
    void tearDown() {

        /*
         * Delete child records first because of foreign keys.
         */

        if (testLoanId != 0) {
            appController.deleteLoan(testLoanId);
        }

        if (testApplicationId != 0) {
            appController.deleteApplication(testApplicationId);
        }

        if (testLoanTypeId != 0) {
            appController.deleteLoanType(testLoanTypeId);
        }

        if (testCustomerId != 0) {
            appController.deleteCustomer(testCustomerId);
        }

        if (testUserId != 0) {
            appController.deleteUser(testUserId);
        }
    }


    // ============================================================
    // USER
    // ============================================================

    @Test
    void addUser() {

        User user = createTestUser();

        appController.addUser(user);

        testUserId =
                getUserIdByUsername(testUsername);

        assertTrue(testUserId > 0);
    }


    @Test
    void getUserById() {

        addTestUser();

        User result =
                appController.getUserById(testUserId);

        assertNotNull(result);

        assertEquals(
                testUsername,
                result.getUsername()
        );
    }


    @Test
    void updateUser() {

        addTestUser();

        User updatedUser = new User(
                testUserId,
                testUsername,
                "updatedPassword",
                "CUSTOMER",
                "ACTIVE",
                null
        );

        appController.updateUser(updatedUser);

        User fetchedUser =
                appController.getUserById(testUserId);

        assertNotNull(fetchedUser);

        assertEquals(
                "updatedPassword",
                fetchedUser.getPasswordHash()
        );
    }


    @Test
    void deleteUser() {

        addTestUser();

        appController.deleteUser(testUserId);

        User deletedUser =
                appController.getUserById(testUserId);

        assertNull(deletedUser);

        testUserId = 0;
    }


    // ============================================================
    // CUSTOMER
    // ============================================================

    @Test
    void addCustomer() {

        addTestUser();

        Customer customer =
                createTestCustomer(testUserId);

        appController.addCustomer(customer);

        testCustomerId =
                getCustomerIdByEmail(testEmail);

        assertTrue(testCustomerId > 0);
    }


    @Test
    void getCustomerById() {

        addTestCustomer();

        Customer result =
                appController.getCustomerById(
                        testCustomerId
                );

        assertNotNull(result);

        assertEquals(
                testEmail,
                result.getEmail()
        );
    }


    @Test
    void updateCustomer() {

        addTestCustomer();

        Customer customer =
                createTestCustomer(testUserId);

        customer.setCustomerId(testCustomerId);

        customer.setFullName(
                "Updated JUnit Customer"
        );

        appController.updateCustomer(customer);

        Customer updatedCustomer =
                appController.getCustomerById(
                        testCustomerId
                );

        assertNotNull(updatedCustomer);

        assertEquals(
                "Updated JUnit Customer",
                updatedCustomer.getFullName()
        );
    }


    @Test
    void deleteCustomer() {

        addTestCustomer();

        appController.deleteCustomer(
                testCustomerId
        );

        Customer deletedCustomer =
                appController.getCustomerById(
                        testCustomerId
                );

        assertNull(deletedCustomer);

        testCustomerId = 0;
    }


    // ============================================================
    // LOAN TYPE
    // ============================================================

    @Test
    void addLoanType() {

        LoanType loanType =
                createTestLoanType();

        appController.addLoanType(loanType);

        testLoanTypeId =
                getLoanTypeIdByName(
                        testLoanTypeName
                );

        assertTrue(testLoanTypeId > 0);
    }


    @Test
    void getLoanTypeById() {

        addTestLoanType();

        LoanType result =
                appController.getLoanTypeById(
                        testLoanTypeId
                );

        assertNotNull(result);

        assertEquals(
                testLoanTypeName,
                result.getName()
        );
    }


    @Test
    void updateLoanType() {

        addTestLoanType();

        LoanType loanType =
                createTestLoanType();

        loanType.setLoanTypeId(
                testLoanTypeId
        );

        loanType.setName(
                testLoanTypeName + " Updated"
        );

        appController.updateLoanType(
                loanType
        );

        LoanType updatedLoanType =
                appController.getLoanTypeById(
                        testLoanTypeId
                );

        assertNotNull(updatedLoanType);

        assertEquals(
                testLoanTypeName + " Updated",
                updatedLoanType.getName()
        );
    }


    @Test
    void deleteLoanType() {

        addTestLoanType();

        appController.deleteLoanType(
                testLoanTypeId
        );

        LoanType deletedLoanType =
                appController.getLoanTypeById(
                        testLoanTypeId
                );

        assertNull(deletedLoanType);

        testLoanTypeId = 0;
    }


    // ============================================================
    // LOAN APPLICATION
    // ============================================================

    @Test
    void addApplication() {

        addTestCustomer();
        addTestLoanType();

        LoanApplication application =
                createTestLoanApplication(
                        testCustomerId,
                        testLoanTypeId,
                        testUserId
                );

        appController.addApplication(
                application
        );

        testApplicationId =
                getLoanApplicationId(
                        testCustomerId,
                        testLoanTypeId
                );

        assertTrue(testApplicationId > 0);
    }


    @Test
    void getApplicationById() {

        addTestLoanApplication();

        LoanApplication result =
                appController.getApplicationById(
                        testApplicationId
                );

        assertNotNull(result);

        assertEquals(
                testCustomerId,
                result.getCustomerId()
        );

        assertEquals(
                testLoanTypeId,
                result.getLoanTypeId()
        );
    }


    @Test
    void updateApplication() {

        addTestLoanApplication();

        LoanApplication application =
                createTestLoanApplication(
                        testCustomerId,
                        testLoanTypeId,
                        testUserId
                );

        application.setApplicationId(
                testApplicationId
        );

        application.setPurpose(
                "Updated JUnit Purpose"
        );

        appController.updateApplication(
                application
        );

        LoanApplication updatedApplication =
                appController.getApplicationById(
                        testApplicationId
                );

        assertNotNull(updatedApplication);

        assertEquals(
                "Updated JUnit Purpose",
                updatedApplication.getPurpose()
        );
    }


    @Test
    void deleteApplication() {

        addTestLoanApplication();

        appController.deleteApplication(
                testApplicationId
        );

        LoanApplication deletedApplication =
                appController.getApplicationById(
                        testApplicationId
                );

        assertNull(deletedApplication);

        testApplicationId = 0;
    }


    // ============================================================
    // LOAN
    // ============================================================

    @Test
    void addLoan() {

        addTestLoanApplication();

        Loan loan =
                createTestLoan(
                        testApplicationId,
                        testCustomerId,
                        testLoanTypeId
                );

        appController.addLoan(loan);

        testLoanId =
                getLoanIdByApplicationId(
                        testApplicationId
                );

        assertTrue(testLoanId > 0);
    }


    @Test
    void getLoanById() {

        addTestLoan();

        Loan result =
                appController.getLoanById(
                        testLoanId
                );

        assertNotNull(result);

        assertEquals(
                testCustomerId,
                result.getCustomerId()
        );

        assertEquals(
                testApplicationId,
                result.getApplicationId()
        );
    }


    @Test
    void updateLoan() {

        addTestLoan();

        Loan loan =
                createTestLoan(
                        testApplicationId,
                        testCustomerId,
                        testLoanTypeId
                );

        loan.setLoanId(testLoanId);

        loan.setPrincipalAmount(
                150000.0
        );

        appController.updateLoan(loan);

        Loan updatedLoan =
                appController.getLoanById(
                        testLoanId
                );

        assertNotNull(updatedLoan);

        assertEquals(
                150000.0,
                updatedLoan.getPrincipalAmount()
        );
    }


    @Test
    void deleteLoan() {

        addTestLoan();

        appController.deleteLoan(
                testLoanId
        );

        Loan deletedLoan =
                appController.getLoanById(
                        testLoanId
                );

        assertNull(deletedLoan);

        testLoanId = 0;
    }


    // ============================================================
    // TEST DATA HELPERS
    // ============================================================

    private User createTestUser() {

        return new User(
                0,
                testUsername,
                "test123",
                "CUSTOMER",
                "ACTIVE",
                null
        );
    }


    private Customer createTestCustomer(
            int userId
    ) {

        return new Customer(
                0,
                userId,
                "JUnit Customer",
                testEmail,
                "9876543210",
                "2000-01-01",
                "Hyderabad",
                50000.0,
                "ABCDE1234F",
                "1234",
                "SALARIED",
                "1234567890",
                "UBIN0001234",
                "Union Bank",
                KycStatus.PENDING,
                null,
                userId,
                null,
                750,
                5000.0,
                CustomerStatus.ACTIVE
        );
    }


    private LoanType createTestLoanType() {

        return new LoanType(
                0,
                testLoanTypeName,
                "JUnit Test Loan",
                10.5,
                50000.0,
                500000.0,
                60,
                "ACTIVE"
        );
    }


    private LoanApplication createTestLoanApplication(
            int customerId,
            int loanTypeId,
            int reviewedBy
    ) {

        return new LoanApplication(
                0,
                customerId,
                loanTypeId,
                100000.0,
                24,
                "JUnit Test Purpose",
                "PENDING",
                null,
                reviewedBy,
                null,
                null
        );
    }


    private Loan createTestLoan(
            int applicationId,
            int customerId,
            int loanTypeId
    ) {

        return new Loan(
                0,
                applicationId,
                customerId,
                loanTypeId,
                100000.0,
                10.5,
                24,
                4639.0,
                "2026-09-25",
                "2028-09-25",
                100000.0,
                "ACTIVE"
        );
    }


    // ============================================================
    // PREPARE TEST RECORDS
    // ============================================================

    private void addTestUser() {

        User user = createTestUser();

        appController.addUser(user);

        testUserId =
                getUserIdByUsername(testUsername);

        assertTrue(testUserId > 0);
    }


    private void addTestCustomer() {

        addTestUser();

        Customer customer =
                createTestCustomer(testUserId);

        appController.addCustomer(customer);

        testCustomerId =
                getCustomerIdByEmail(testEmail);

        assertTrue(testCustomerId > 0);
    }


    private void addTestLoanType() {

        LoanType loanType =
                createTestLoanType();

        appController.addLoanType(loanType);

        testLoanTypeId =
                getLoanTypeIdByName(
                        testLoanTypeName
                );

        assertTrue(testLoanTypeId > 0);
    }


    private void addTestLoanApplication() {

        addTestCustomer();
        addTestLoanType();

        LoanApplication application =
                createTestLoanApplication(
                        testCustomerId,
                        testLoanTypeId,
                        testUserId
                );

        appController.addApplication(
                application
        );

        testApplicationId =
                getLoanApplicationId(
                        testCustomerId,
                        testLoanTypeId
                );

        assertTrue(testApplicationId > 0);
    }


    private void addTestLoan() {

        addTestLoanApplication();

        Loan loan =
                createTestLoan(
                        testApplicationId,
                        testCustomerId,
                        testLoanTypeId
                );

        appController.addLoan(loan);

        testLoanId =
                getLoanIdByApplicationId(
                        testApplicationId
                );

        assertTrue(testLoanId > 0);
    }


    // ============================================================
    // DATABASE LOOKUP HELPERS
    // ============================================================

    private int getUserIdByUsername(
            String username
    ) {

        String sql = """
                SELECT user_id
                FROM users
                WHERE username = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(resultSet.next());

                return resultSet.getInt(
                        "user_id"
                );
            }

        } catch (Exception e) {

            fail(
                    "Unable to find test user: "
                            + e.getMessage()
            );

            return 0;
        }
    }


    private int getCustomerIdByEmail(
            String email
    ) {

        String sql = """
                SELECT customer_id
                FROM customers
                WHERE email = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, email);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(resultSet.next());

                return resultSet.getInt(
                        "customer_id"
                );
            }

        } catch (Exception e) {

            fail(
                    "Unable to find test customer: "
                            + e.getMessage()
            );

            return 0;
        }
    }


    private int getLoanTypeIdByName(
            String name
    ) {

        String sql = """
                SELECT loan_type_id
                FROM loan_types
                WHERE name = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(resultSet.next());

                return resultSet.getInt(
                        "loan_type_id"
                );
            }

        } catch (Exception e) {

            fail(
                    "Unable to find test loan type: "
                            + e.getMessage()
            );

            return 0;
        }
    }


    private int getLoanApplicationId(
            int customerId,
            int loanTypeId
    ) {

        String sql = """
                SELECT application_id
                FROM loan_applications
                WHERE customer_id = ?
                AND loan_type_id = ?
                ORDER BY application_id DESC
                LIMIT 1
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);
            statement.setInt(2, loanTypeId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(resultSet.next());

                return resultSet.getInt(
                        "application_id"
                );
            }

        } catch (Exception e) {

            fail(
                    "Unable to find test application: "
                            + e.getMessage()
            );

            return 0;
        }
    }


    private int getLoanIdByApplicationId(
            int applicationId
    ) {

        String sql = """
                SELECT loan_id
                FROM loans
                WHERE application_id = ?
                """;

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, applicationId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(resultSet.next());

                return resultSet.getInt(
                        "loan_id"
                );
            }

        } catch (Exception e) {

            fail(
                    "Unable to find test loan: "
                            + e.getMessage()
            );

            return 0;
        }
    }
}