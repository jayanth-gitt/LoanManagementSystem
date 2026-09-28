package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppControllerTest {

    private AppController appController;

    private String testUsername;
    private String testEmail;
    private String testPan;
    private String testPhone;
    private String testAccountNumber;

    private int testUserId;
    private int testCustomerId;
    private int testApplicationId;
    private int testLoanId;

    /*
     * Existing Loan Officer created in the database.
     */
    private static final int LOAN_OFFICER_ID = 200;

    /*
     * Existing active loan type from the database.
     */
    private static final int LOAN_TYPE_ID = 25;


    // =========================================================
    // SETUP
    // =========================================================

    @BeforeEach
    void setUp() {

        appController = new AppController();

        String uniqueId =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8);

        testUsername =
                "junit_user_" + uniqueId;

        testEmail =
                "junit_" + uniqueId + "@test.com";

        testPan =
                "ABC" +
                        uniqueId.substring(0, 5).toUpperCase() +
                        "F";

        /*
         * Generate a valid 10-digit phone number.
         */
        long number =
                9000000000L
                        + Math.abs(uniqueId.hashCode() % 1000000000);

        testPhone =
                String.valueOf(number);

        /*
         * Generate a unique 10-digit account number.
         */
        testAccountNumber =
                "1" +
                        String.format(
                                "%09d",
                                Math.abs(uniqueId.hashCode())
                                        % 1000000000
                        );
    }


    // =========================================================
    // CLEANUP
    // =========================================================

    @AfterEach
    void tearDown() {

        /*
         * Child records must be deleted before parent records.
         */

        if (testLoanId != 0) {

            appController.deleteLoan(
                    testLoanId
            );

            testLoanId = 0;
        }

        if (testApplicationId != 0) {

            appController.deleteLoanApplication(
                    testApplicationId
            );

            testApplicationId = 0;
        }

        if (testCustomerId != 0) {

            appController.deleteCustomer(
                    testCustomerId
            );

            testCustomerId = 0;
        }

        if (testUserId != 0) {

            appController.deleteUser(
                    testUserId
            );

            testUserId = 0;
        }
    }


    // =========================================================
    // USER TEST
    // =========================================================

    @Test
    void addUser() {

        User user = createTestUser();

        int rowsAffected =
                appController.addUser(user);

        assertEquals(
                1,
                rowsAffected
        );

        testUserId =
                getUserIdByUsername(
                        testUsername
                );

        assertTrue(
                testUserId > 0
        );
    }


    // =========================================================
    // CUSTOMER TEST
    // =========================================================

    @Test
    void addCustomer() {

        addTestUser();

        Customer customer =
                createTestCustomer(
                        testUserId
                );

        int rowsAffected =
                appController.addCustomer(
                        customer
                );

        assertEquals(
                1,
                rowsAffected
        );

        testCustomerId =
                getCustomerIdByEmail(
                        testEmail
                );

        assertTrue(
                testCustomerId > 0
        );
    }


    // =========================================================
    // KYC TEST
    // =========================================================

    @Test
    void verifyCustomerKyc() {

        addTestCustomer();

        int rowsAffected =
                appController.verifyCustomerKyc(
                        testCustomerId,
                        LOAN_OFFICER_ID
                );

        assertEquals(
                1,
                rowsAffected
        );

        Customer customer =
                appController.getCustomerById(
                        testCustomerId
                );

        assertNotNull(customer);

        assertEquals(
                KycStatus.VERIFIED,
                customer.getKycStatus()
        );
    }


    // =========================================================
    // LOAN APPLICATION TEST
    // =========================================================

    @Test
    void addLoanApplication() {

        addTestCustomer();

        /*
         * Loan application requires VERIFIED KYC.
         */
        int kycResult =
                appController.verifyCustomerKyc(
                        testCustomerId,
                        LOAN_OFFICER_ID
                );

        assertEquals(
                1,
                kycResult
        );

        LoanApplication application =
                createTestLoanApplication();

        int rowsAffected =
                appController.addLoanApplication(
                        application
                );

        assertEquals(
                1,
                rowsAffected
        );

        testApplicationId =
                getLoanApplicationId(
                        testCustomerId
                );

        assertTrue(
                testApplicationId > 0
        );

        LoanApplication savedApplication =
                appController.getLoanApplicationById(
                        testApplicationId
                );

        assertNotNull(
                savedApplication
        );

        /*
         * New applications must start as PENDING.
         */
        assertEquals(
                "PENDING",
                savedApplication.getStatus()
        );
    }


    // =========================================================
    // APPROVE APPLICATION TEST
    // =========================================================

    @Test
    void approveLoanApplication() {

        addLoanApplication();

        int rowsAffected =
                appController.approveLoanApplication(
                        testApplicationId,
                        LOAN_OFFICER_ID,
                        "Application approved for loan creation"
                );

        assertEquals(
                1,
                rowsAffected
        );

        LoanApplication application =
                appController.getLoanApplicationById(
                        testApplicationId
                );

        assertNotNull(
                application
        );

        assertEquals(
                "APPROVED",
                application.getStatus()
        );

        assertEquals(
                LOAN_OFFICER_ID,
                application.getReviewedBy()
        );

        assertEquals(
                "Application approved for loan creation",
                application.getRemarks()
        );
    }


    // =========================================================
    // REJECT APPLICATION TEST
    // =========================================================

    @Test
    void rejectLoanApplication() {

        addLoanApplication();

        int rowsAffected =
                appController.rejectLoanApplication(
                        testApplicationId,
                        LOAN_OFFICER_ID,
                        "Insufficient documentation"
                );

        assertEquals(
                1,
                rowsAffected
        );

        LoanApplication application =
                appController.getLoanApplicationById(
                        testApplicationId
                );

        assertNotNull(
                application
        );

        assertEquals(
                "REJECTED",
                application.getStatus()
        );

        assertEquals(
                LOAN_OFFICER_ID,
                application.getReviewedBy()
        );

        assertEquals(
                "Insufficient documentation",
                application.getRemarks()
        );
    }


    // =========================================================
    // LOAN CREATION TEST
    // =========================================================

    @Test
    void addLoan() {

        /*
         * Create customer
         * → verify KYC
         * → create application
         */
        addLoanApplication();

        /*
         * Application must be APPROVED
         * before loan creation.
         */
        int approvalResult =
                appController.approveLoanApplication(
                        testApplicationId,
                        LOAN_OFFICER_ID,
                        "Approved for loan creation"
                );

        assertEquals(
                1,
                approvalResult
        );

        /*
         * Create an empty loan object.
         *
         * LoanServiceImpl will populate:
         * customerId
         * loanTypeId
         * principalAmount
         * interestRate
         * tenure
         * EMI
         * startDate
         * endDate
         * outstandingAmount
         * status
         */
        Loan loan =
                new Loan(
                        0,
                        testApplicationId,
                        0,
                        0,
                        0.0,
                        0.0,
                        0,
                        0.0,
                        null,
                        null,
                        0.0,
                        null
                );

        int rowsAffected =
                appController.addLoan(
                        loan
                );

        assertEquals(
                1,
                rowsAffected
        );

        testLoanId =
                getLoanIdByApplicationId(
                        testApplicationId
                );

        assertTrue(
                testLoanId > 0
        );

        /*
         * Fetch the created loan.
         */
        Loan savedLoan =
                appController.getLoanById(
                        testLoanId
                );

        assertNotNull(
                savedLoan
        );

        /*
         * Verify basic loan values.
         */
        assertEquals(
                testApplicationId,
                savedLoan.getApplicationId()
        );

        assertEquals(
                testCustomerId,
                savedLoan.getCustomerId()
        );

        assertEquals(
                LOAN_TYPE_ID,
                savedLoan.getLoanTypeId()
        );

        assertEquals(
                100000.0,
                savedLoan.getPrincipalAmount()
        );

        /*
         * Loan type 25 currently has 10.5% interest.
         */
        assertEquals(
                10.5,
                savedLoan.getInterestRate(),
                0.01
        );

        assertEquals(
                24,
                savedLoan.getTenureMonths()
        );

        /*
         * EMI must have been calculated.
         */
        assertTrue(
                savedLoan.getEmiAmount() > 0
        );

        /*
         * Outstanding starts at principal amount.
         */
        assertEquals(
                100000.0,
                savedLoan.getOutstandingAmount(),
                0.01
        );

        /*
         * New loan must be ACTIVE.
         */
        assertEquals(
                "ACTIVE",
                savedLoan.getStatus()
        );

        /*
         * Dates must be populated.
         */
        assertNotNull(
                savedLoan.getStartDate()
        );

        assertNotNull(
                savedLoan.getEndDate()
        );
    }


    // =========================================================
    // DUPLICATE LOAN TEST
    // =========================================================

    @Test
    void duplicateLoanForSameApplication() {

        addLoanApplication();

        int approvalResult =
                appController.approveLoanApplication(
                        testApplicationId,
                        LOAN_OFFICER_ID,
                        "Approved for loan creation"
                );

        assertEquals(
                1,
                approvalResult
        );

        Loan firstLoan =
                new Loan(
                        0,
                        testApplicationId,
                        0,
                        0,
                        0.0,
                        0.0,
                        0,
                        0.0,
                        null,
                        null,
                        0.0,
                        null
                );

        int firstResult =
                appController.addLoan(
                        firstLoan
                );

        assertEquals(
                1,
                firstResult
        );

        testLoanId =
                getLoanIdByApplicationId(
                        testApplicationId
                );

        /*
         * Try creating another loan for the
         * same application.
         *
         * application_id has a UNIQUE constraint
         * in the database.
         */
        Loan secondLoan =
                new Loan(
                        0,
                        testApplicationId,
                        0,
                        0,
                        0.0,
                        0.0,
                        0,
                        0.0,
                        null,
                        null,
                        0.0,
                        null
                );

        int secondResult =
                appController.addLoan(
                        secondLoan
                );

        assertEquals(
                0,
                secondResult
        );
    }


    // =========================================================
    // APPROVED APPLICATION IS REQUIRED
    // =========================================================

    @Test
    void loanCannotBeCreatedFromPendingApplication() {

        addLoanApplication();

        /*
         * Application is still PENDING.
         */
        Loan loan =
                new Loan(
                        0,
                        testApplicationId,
                        0,
                        0,
                        0.0,
                        0.0,
                        0,
                        0.0,
                        null,
                        null,
                        0.0,
                        null
                );

        assertThrows(
                IllegalStateException.class,
                () -> appController.addLoan(loan)
        );
    }


    // =========================================================
    // HELPER: CREATE USER
    // =========================================================

    private User createTestUser() {

        String createdAt =
                LocalDateTime.now()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd HH:mm:ss"
                                )
                        );

        return new User(
                0,
                testUsername,
                "test123",
                "CUSTOMER",
                "ACTIVE",
                createdAt
        );
    }


    // =========================================================
    // HELPER: CREATE CUSTOMER
    // =========================================================

    private Customer createTestCustomer(
            int userId
    ) {

        return new Customer(
                0,
                userId,
                "JUnit Customer",
                testEmail,
                testPhone,
                "2000-01-01",
                "Hyderabad",
                50000.0,
                testPan,
                "1234",
                "SALARIED",
                testAccountNumber,
                "UBIN0001234",
                "Union Bank",
                KycStatus.PENDING,
                null,
                0,
                null,
                750,
                5000.0,
                CustomerStatus.ACTIVE
        );
    }


    // =========================================================
    // HELPER: CREATE LOAN APPLICATION
    // =========================================================

    private LoanApplication createTestLoanApplication() {

        return new LoanApplication(
                0,
                testCustomerId,
                LOAN_TYPE_ID,
                100000.0,
                24,
                "JUnit Test Purpose",
                "PENDING",
                null,
                0,
                null,
                null
        );
    }


    // =========================================================
    // HELPER: ADD TEST USER
    // =========================================================

    private void addTestUser() {

        User user =
                createTestUser();

        int rowsAffected =
                appController.addUser(
                        user
                );

        assertEquals(
                1,
                rowsAffected
        );

        testUserId =
                getUserIdByUsername(
                        testUsername
                );

        assertTrue(
                testUserId > 0
        );
    }


    // =========================================================
    // HELPER: ADD TEST CUSTOMER
    // =========================================================

    private void addTestCustomer() {

        addTestUser();

        Customer customer =
                createTestCustomer(
                        testUserId
                );

        int rowsAffected =
                appController.addCustomer(
                        customer
                );

        assertEquals(
                1,
                rowsAffected
        );

        testCustomerId =
                getCustomerIdByEmail(
                        testEmail
                );

        assertTrue(
                testCustomerId > 0
        );
    }


    // =========================================================
    // HELPER: GET USER ID
    // =========================================================

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

            statement.setString(
                    1,
                    username
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(
                        resultSet.next()
                );

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


    // =========================================================
    // HELPER: GET CUSTOMER ID
    // =========================================================

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

            statement.setString(
                    1,
                    email
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(
                        resultSet.next()
                );

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


    // =========================================================
    // HELPER: GET APPLICATION ID
    // =========================================================

    private int getLoanApplicationId(
            int customerId
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

            statement.setInt(
                    1,
                    customerId
            );

            statement.setInt(
                    2,
                    LOAN_TYPE_ID
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(
                        resultSet.next()
                );

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


    // =========================================================
    // HELPER: GET LOAN ID
    // =========================================================

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

            statement.setInt(
                    1,
                    applicationId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                assertTrue(
                        resultSet.next()
                );

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