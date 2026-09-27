package org.example;

import com.loanmanagement.controller.AppController;
import com.loanmanagement.model.Customer;
import com.loanmanagement.model.CustomerStatus;
import com.loanmanagement.model.KycStatus;
import com.loanmanagement.model.User;
import com.loanmanagement.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    private static final String SELECT_USER_ID_BY_USERNAME_SQL = """
            SELECT user_id
            FROM users
            WHERE username = ?
            """;

    private static final String SELECT_CUSTOMER_ID_BY_EMAIL_SQL = """
            SELECT customer_id
            FROM customers
            WHERE email = ?
            """;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AppController appController = new AppController();

        System.out.println();
        System.out.println("======================================");
        System.out.println("       LOAN MANAGEMENT SYSTEM");
        System.out.println("======================================");

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("========== MAIN MENU ==========");
            System.out.println("1. Create User");
            System.out.println("2. Create Customer");
            System.out.println("3. Exit");
            System.out.println("===============================");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    createUser(scanner, appController);
                    break;

                case 2:
                    createCustomer(scanner, appController);
                    break;

                case 3:
                    running = false;
                    System.out.println("Exiting Loan Management System...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    // ============================================================
    // CREATE USER
    // ============================================================

    private static void createUser(
            Scanner scanner,
            AppController appController) {

        System.out.println();
        System.out.println("========== CREATE USER ==========");

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Role: ");
        String role = scanner.nextLine();

        User user = new User(
                0,
                username,
                password,
                role,
                "ACTIVE",
                null
        );

        int result = appController.addUser(user);

        if (result == 1) {

            System.out.println();
            System.out.println("User created successfully!");

            int userId = getUserIdByUsername(username);

            if (userId > 0) {
                System.out.println("Generated User ID: " + userId);
            }

        } else {

            System.out.println();
            System.out.println("User creation failed.");
        }
    }

    // ============================================================
    // CREATE CUSTOMER
    // ============================================================

    private static void createCustomer(
            Scanner scanner,
            AppController appController) {

        System.out.println();
        System.out.println("========== CREATE CUSTOMER ==========");

        System.out.print("Enter User ID: ");
        int userId;

        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid User ID.");
            return;
        }

        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Monthly Income: ");
        double monthlyIncome;

        try {
            monthlyIncome = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid monthly income.");
            return;
        }

        System.out.print("Enter PAN Number: ");
        String panNumber = scanner.nextLine();

        System.out.print("Enter Aadhaar Last 4 Digits: ");
        String aadhaarLast4 = scanner.nextLine();

        System.out.print("Enter Employment Type: ");
        String employmentType = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter IFSC Code: ");
        String ifscCode = scanner.nextLine();

        System.out.print("Enter Bank Name: ");
        String bankName = scanner.nextLine();

        Customer customer = new Customer(
                0,
                userId,
                fullName,
                email,
                phone,
                dob,
                address,
                monthlyIncome,
                panNumber,
                aadhaarLast4,
                employmentType,
                accountNumber,
                ifscCode,
                bankName,
                KycStatus.PENDING,
                null,
                userId,
                null,
                0,
                0.0,
                CustomerStatus.ACTIVE
        );

        int result = appController.addCustomer(customer);

        if (result == 1) {

            System.out.println();
            System.out.println("Customer created successfully!");

            int customerId = getCustomerIdByEmail(email);

            if (customerId > 0) {
                System.out.println(
                        "Generated Customer ID: " + customerId
                );
            }

        } else {

            System.out.println();
            System.out.println("Customer creation failed.");
        }
    }

    // ============================================================
    // GET USER ID
    // ============================================================

    private static int getUserIdByUsername(String username) {

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                SELECT_USER_ID_BY_USERNAME_SQL)
        ) {

            statement.setString(1, username);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve User ID: "
                            + e.getMessage()
            );
        }

        return 0;
    }

    // ============================================================
    // GET CUSTOMER ID
    // ============================================================

    private static int getCustomerIdByEmail(String email) {

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                SELECT_CUSTOMER_ID_BY_EMAIL_SQL)
        ) {

            statement.setString(1, email);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("customer_id");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve Customer ID: "
                            + e.getMessage()
            );
        }

        return 0;
    }
}