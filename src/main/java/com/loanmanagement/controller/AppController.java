package com.loanmanagement.controller;

import com.loanmanagement.model.Customer;
import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.model.User;
import com.loanmanagement.service.*;
import com.loanmanagement.service.impl.*;

import java.util.List;
import java.util.Scanner;

import java.util.List;
import java.util.Scanner;

public class AppController {

    private static final Scanner sc = new Scanner(System.in);

    private static final UserController userController =
            new UserController();

    private static final CustomerController customerController =
            new CustomerController();

    private static final LoanTypeController loanTypeController =
            new LoanTypeController();

    private static final LoanApplicationController applicationController =
            new LoanApplicationController();

    private static final LoanController loanController =
            new LoanController();

    private static final AuthController authController =
            new AuthController();

    public static void main(String[] args) {

        String loggedInUsername = null;
        String loggedInRole = null;
        int loggedInCustomerId = 0;

        while (true) {

            // =========================================================
            // LOGIN MENU
            // =========================================================

            if (loggedInUsername == null) {

                System.out.println("\n================================");
                System.out.println("      LOAN MANAGEMENT SYSTEM");
                System.out.println("================================");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");

                int choice = readInt();

                if (choice == 1) {

                    while (true) {

                        try {

                            System.out.println("\n========== LOGIN ==========");

                            System.out.print("Enter Username: ");
                            String username = sc.nextLine();

                            System.out.print("Enter Password: ");
                            String password = sc.nextLine();

                            boolean loggedIn =
                                    authController.login(
                                            username,
                                            password);

                            if (!loggedIn) {

                                System.out.println(
                                        "\nLogin failed. Invalid credentials or inactive user.");

                                System.out.println(
                                        "Please try again.");

                                continue;
                            }

                            User user =
                                    userController
                                            .getUserByUsername(username);

                            if (user == null) {

                                System.out.println(
                                        "Unable to retrieve user details.");

                                continue;
                            }

                            loggedInUsername = username;
                            loggedInRole = user.getRole();

                            // CUSTOMER
                            if ("CUSTOMER".equalsIgnoreCase(
                                    loggedInRole)) {

                                Customer customer =
                                        customerController
                                                .getCustomerByUsername(
                                                        username);

                                if (customer == null) {

                                    System.out.println(
                                            "Customer profile not found.");

                                    loggedInUsername = null;
                                    loggedInRole = null;

                                    continue;
                                }

                                loggedInCustomerId =
                                        customer.getCustomerId();

                                System.out.println(
                                        "\nLogin successful!");
                                User user1 =
                                        userController.getUserByUsername(username);

                                if (user == null) {
                                    System.out.println("User details not found.");
                                    continue;
                                }

                                String role = user.getRole();

                                if ("CUSTOMER".equalsIgnoreCase(role)) {

                                    Customer customer1 =
                                            customerController.getCustomerByUsername(username);

                                    if (customer == null) {
                                        System.out.println("Customer profile not found.");
                                        continue;
                                    }

                                    int customerId = customer.getCustomerId();

                                    System.out.println(
                                            "Customer ID: " + customerId);
                                }

                                System.out.println(
                                        "Customer ID: "
                                                + loggedInCustomerId);

                            } else {

                                System.out.println(
                                        "\nLogin successful!");

                                System.out.println(
                                        "Role: " + loggedInRole);
                            }

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "\nLogin failed: "
                                            + e.getMessage());

                            System.out.println(
                                    "Please try again.");
                        }
                    }

                } else if (choice == 2) {

                    System.out.println(
                            "Thank you for using Loan Management System. Exiting...");

                    sc.close();
                    return;

                } else {

                    System.out.println(
                            "Invalid choice. Please enter 1 or 2.");
                }

                continue;
            }

            // =========================================================
            // ADMIN
            // =========================================================

            if ("ADMIN".equalsIgnoreCase(loggedInRole)) {

                boolean logout = showAdminMenu();

                if (logout) {

                    loggedInUsername = null;
                    loggedInRole = null;
                }

                continue;
            }

            // =========================================================
            // LOAN OFFICER
            // =========================================================

            if ("LOAN_OFFICER".equalsIgnoreCase(loggedInRole)) {

                boolean logout = showLoanOfficerMenu();

                if (logout) {

                    loggedInUsername = null;
                    loggedInRole = null;
                }

                continue;
            }

            // =========================================================
            // CUSTOMER
            // =========================================================

            if ("CUSTOMER".equalsIgnoreCase(loggedInRole)) {

                boolean logout =
                        showCustomerMenu(
                                loggedInUsername,
                                loggedInCustomerId);

                if (logout) {

                    loggedInUsername = null;
                    loggedInRole = null;
                    loggedInCustomerId = 0;
                }

                continue;
            }

            System.out.println("Invalid user role.");

            loggedInUsername = null;
            loggedInRole = null;
            loggedInCustomerId = 0;
        }
    }

    // ================================================================
    // ADMIN MENU
    // ================================================================

    private static boolean showAdminMenu() {

        while (true) {

            System.out.println("\n================================");
            System.out.println("           ADMIN MENU");
            System.out.println("================================");

            System.out.println("1. User Management");
            System.out.println("2. Loan Type Management");
            System.out.println("3. Logout");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    userManagementMenu();
                    break;

                case 2:
                    loanTypeManagementMenu();
                    break;

                case 3:
                    System.out.println(
                            "Logged out successfully.");
                    return true;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // LOAN OFFICER MENU
    // ================================================================

    private static boolean showLoanOfficerMenu() {

        while (true) {

            System.out.println("\n================================");
            System.out.println("       LOAN OFFICER MENU");
            System.out.println("================================");

            System.out.println("1. Customer Management");
            System.out.println("2. Loan Application Management");
            System.out.println("3. Loan Management");
            System.out.println("4. Loan Approval / Rejection");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    customerManagementMenu();
                    break;

                case 2:
                    applicationManagementMenu();
                    break;

                case 3:
                    loanManagementMenu();
                    break;

                case 4:
                    officerReviewApplication();
                    break;

                case 5:
                    System.out.println(
                            "Logged out successfully.");
                    return true;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // CUSTOMER MENU
    // ================================================================

    private static boolean showCustomerMenu(
            String username,
            int customerId) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("          CUSTOMER MENU");
            System.out.println("================================");

            System.out.println(
                    "Customer ID: " + customerId);

            System.out.println("1. View My Profile");
            System.out.println("2. Update My Profile");
            System.out.println("3. View Available Loan Types");
            System.out.println("4. Apply for Loan");
            System.out.println("5. View My Applications");
            System.out.println("6. View My Loans");
            System.out.println("7. Change Password");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    viewCustomerProfile(username);
                    break;

                case 2:
                    updateCustomerProfile(username);
                    break;

                case 3:
                    viewAvailableLoanTypes();
                    break;

                case 4:
                    applyForLoan(customerId);
                    break;

                case 5:
                    viewMyApplications(customerId);
                    break;

                case 6:
                    viewMyLoans(customerId);
                    break;

                case 7:
                    changeCustomerPassword(username);
                    break;

                case 8:
                    System.out.println(
                            "Logged out successfully.");
                    return true;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // USER MANAGEMENT
    // ================================================================

    private static void userManagementMenu() {

        while (true) {

            System.out.println("\n========== USER MANAGEMENT ==========");
            System.out.println("1. Register New User");
            System.out.println("2. Get User By ID");
            System.out.println("3. Get All Users");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:

                    while (true) {

                        try {

                            User user = new User();

                            System.out.println(
                                    "\n========== ADD USER ==========");

                            System.out.print("Enter Username: ");
                            user.setUsername(sc.nextLine());

                            System.out.print("Enter Password: ");
                            user.setPassword(sc.nextLine());

                            System.out.print(
                                    "Enter Role (ADMIN/LOAN_OFFICER/CUSTOMER): ");

                            user.setRole(
                                    sc.nextLine().toUpperCase());

                            System.out.print(
                                    "Enter Status (ACTIVE/INACTIVE): ");

                            user.setStatus(
                                    sc.nextLine().toUpperCase());

                            userController.addUser(user);

                            System.out.println(
                                    "\nUser added successfully!");

                            System.out.println(
                                    "Generated User ID: "
                                            + user.getUserId());

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "\nInvalid or duplicate value: "
                                            + e.getMessage());

                            System.out.println(
                                    "Please enter the user details again.");
                        }
                    }

                    break;

                case 2:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== GET USER ==========");

                            System.out.print(
                                    "Enter User ID: ");

                            int userId = readInt();

                            User user =
                                    userController.getUserById(
                                            userId);

                            if (user == null) {

                                System.out.println(
                                        "User not found.");

                                continue;
                            }

                            System.out.println(
                                    "\nUser Details");

                            System.out.println(
                                    "User ID   : "
                                            + user.getUserId());

                            System.out.println(
                                    "Username  : "
                                            + user.getUsername());

                            System.out.println(
                                    "Role      : "
                                            + user.getRole());

                            System.out.println(
                                    "Status    : "
                                            + user.getStatus());

                            System.out.println(
                                    "Created At: "
                                            + user.getCreatedAt());

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to retrieve user: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 3:

                    try {

                        System.out.println(
                                "\n========== ALL USERS ==========");

                        List<User> users =
                                userController.getAllUsers();

                        if (users == null || users.isEmpty()) {

                            System.out.println(
                                    "No users found.");

                        } else {

                            for (User user : users) {

                                System.out.println(
                                        "----------------------------");

                                System.out.println(
                                        "User ID   : "
                                                + user.getUserId());

                                System.out.println(
                                        "Username  : "
                                                + user.getUsername());

                                System.out.println(
                                        "Role      : "
                                                + user.getRole());

                                System.out.println(
                                        "Status    : "
                                                + user.getStatus());

                                System.out.println(
                                        "Created At: "
                                                + user.getCreatedAt());
                            }
                        }

                    } catch (Exception e) {

                        System.out.println(
                                "Unable to retrieve users: "
                                        + e.getMessage());
                    }

                    break;

                case 4:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== UPDATE USER ==========");

                            System.out.print(
                                    "Enter User ID: ");

                            int userId = readInt();

                            User user =
                                    userController.getUserById(
                                            userId);

                            if (user == null) {

                                System.out.println(
                                        "User not found.");

                                continue;
                            }

                            System.out.println(
                                    "\nCurrent User Details:");

                            System.out.println(
                                    "Username : "
                                            + user.getUsername());

                            System.out.println(
                                    "Role     : "
                                            + user.getRole());

                            System.out.println(
                                    "Status   : "
                                            + user.getStatus());

                            System.out.print(
                                    "Enter New Username: ");

                            user.setUsername(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Password: ");

                            user.setPassword(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Role (ADMIN/LOAN_OFFICER/CUSTOMER): ");

                            user.setRole(
                                    sc.nextLine().toUpperCase());

                            System.out.print(
                                    "Enter New Status (ACTIVE/INACTIVE): ");

                            user.setStatus(
                                    sc.nextLine().toUpperCase());

                            userController.updateUser(user);

                            System.out.println(
                                    "User updated successfully.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to update user: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 5:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== DELETE USER ==========");

                            System.out.print(
                                    "Enter User ID: ");

                            int userId = readInt();

                            User user =
                                    userController.getUserById(
                                            userId);

                            if (user == null) {

                                System.out.println(
                                        "User not found.");

                                continue;
                            }

                            System.out.println(
                                    "\nUser ID : "
                                            + user.getUserId());

                            System.out.println(
                                    "Username: "
                                            + user.getUsername());

                            System.out.println(
                                    "Role    : "
                                            + user.getRole());

                            System.out.println(
                                    "Status  : "
                                            + user.getStatus());

                            System.out.print(
                                    "Are you sure? (YES/NO): ");

                            String confirmation =
                                    sc.nextLine();

                            if (!confirmation.equalsIgnoreCase(
                                    "YES")) {

                                System.out.println(
                                        "Delete operation cancelled.");

                                break;
                            }

                            userController.deleteUser(
                                    userId);

                            System.out.println(
                                    "User delete/deactivate operation completed.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to delete user: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 6:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // CUSTOMER MANAGEMENT
    // ================================================================

    private static void customerManagementMenu() {

        while (true) {

            System.out.println(
                    "\n========== CUSTOMER MANAGEMENT ==========");

            System.out.println("1. Create Customer Profile");
            System.out.println("2. Get Customer By ID");
            System.out.println("3. Get All Customers");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:

                    while (true) {

                        try {

                            Customer customer =
                                    new Customer();

                            System.out.println(
                                    "\n========== ADD CUSTOMER ==========");

                            System.out.print(
                                    "Enter User ID: ");

                            customer.setUserId(
                                    readInt());

                            System.out.print(
                                    "Enter Full Name: ");

                            customer.setFullName(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Email: ");

                            customer.setEmail(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Phone: ");

                            customer.setPhone(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter DOB (YYYY-MM-DD): ");

                            customer.setDob(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Address: ");

                            customer.setAddress(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Monthly Income: ");

                            customer.setMonthlyIncome(
                                    readDouble());

                            System.out.print(
                                    "Enter PAN Number: ");

                            customer.setPanNumber(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Aadhaar Last 4 Digits: ");

                            customer.setAadhaarLast4(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Employment Type: ");

                            customer.setEmploymentType(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Account Number: ");

                            customer.setAccountNumber(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter IFSC Code: ");

                            customer.setIfscCode(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Bank Name: ");

                            customer.setBankName(
                                    sc.nextLine());

                            customerController.addCustomer(
                                    customer);

                            System.out.println(
                                    "\nCustomer added successfully!");

                            System.out.println(
                                    "Generated Customer ID: "
                                            + customer.getCustomerId());

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "\nUnable to add customer: "
                                            + e.getMessage());

                            System.out.println(
                                    "Please enter the details again.");
                        }
                    }

                    break;

                case 2:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== GET CUSTOMER ==========");

                            System.out.print(
                                    "Enter Customer ID: ");

                            int customerId =
                                    readInt();

                            Customer customer =
                                    customerController
                                            .getCustomerById(
                                                    customerId);

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found.");

                                continue;
                            }

                            printCustomer(customer);

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to retrieve customer: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 3:

                    try {

                        System.out.println(
                                "\n========== ALL CUSTOMERS ==========");

                        List<Customer> customers =
                                customerController
                                        .getAllCustomers();

                        if (customers == null
                                || customers.isEmpty()) {

                            System.out.println(
                                    "No customers found.");

                        } else {

                            for (Customer customer :
                                    customers) {

                                printCustomer(customer);

                                System.out.println(
                                        "----------------------------");
                            }
                        }

                    } catch (Exception e) {

                        System.out.println(
                                "Unable to retrieve customers: "
                                        + e.getMessage());
                    }

                    break;

                case 4:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== UPDATE CUSTOMER ==========");

                            System.out.print(
                                    "Enter Customer ID: ");

                            int customerId =
                                    readInt();

                            Customer customer =
                                    customerController
                                            .getCustomerById(
                                                    customerId);

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found.");

                                continue;
                            }

                            System.out.println(
                                    "\nCurrent Customer Details:");

                            System.out.println(
                                    "Full Name : "
                                            + customer.getFullName());

                            System.out.println(
                                    "Email     : "
                                            + customer.getEmail());

                            System.out.println(
                                    "Phone     : "
                                            + customer.getPhone());

                            System.out.print(
                                    "Enter New Full Name: ");

                            customer.setFullName(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Email: ");

                            customer.setEmail(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Phone: ");

                            customer.setPhone(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New DOB (YYYY-MM-DD): ");

                            customer.setDob(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Address: ");

                            customer.setAddress(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Monthly Income: ");

                            customer.setMonthlyIncome(
                                    readDouble());

                            System.out.print(
                                    "Enter New PAN Number: ");

                            customer.setPanNumber(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Aadhaar Last 4 Digits: ");

                            customer.setAadhaarLast4(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Employment Type: ");

                            customer.setEmploymentType(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Account Number: ");

                            customer.setAccountNumber(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New IFSC Code: ");

                            customer.setIfscCode(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Bank Name: ");

                            customer.setBankName(
                                    sc.nextLine());

                            customerController
                                    .updateCustomer(
                                            customer);

                            System.out.println(
                                    "Customer updated successfully.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to update customer: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 5:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== DELETE CUSTOMER ==========");

                            System.out.print(
                                    "Enter Customer ID: ");

                            int customerId =
                                    readInt();

                            Customer customer =
                                    customerController
                                            .getCustomerById(
                                                    customerId);

                            if (customer == null) {

                                System.out.println(
                                        "Customer not found.");

                                continue;
                            }

                            System.out.println(
                                    "Customer: "
                                            + customer.getFullName());

                            System.out.print(
                                    "Are you sure? (YES/NO): ");

                            String confirmation =
                                    sc.nextLine();

                            if (!confirmation.equalsIgnoreCase(
                                    "YES")) {

                                System.out.println(
                                        "Delete operation cancelled.");

                                break;
                            }

                            customerController
                                    .deleteCustomer(
                                            customerId);

                            System.out.println(
                                    "Customer delete/deactivate operation completed.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to delete customer: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 6:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // LOAN TYPE MANAGEMENT
    // ================================================================

    private static void loanTypeManagementMenu() {

        while (true) {

            System.out.println(
                    "\n========== LOAN TYPE MANAGEMENT ==========");

            System.out.println("1. Add Loan Type");
            System.out.println("2. Get Loan Type By ID");
            System.out.println("3. Get All Loan Types");
            System.out.println("4. Update Loan Type");
            System.out.println("5. Delete Loan Type");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:

                    while (true) {

                        try {

                            LoanType loanType =
                                    new LoanType();

                            System.out.println(
                                    "\n========== ADD LOAN TYPE ==========");

                            System.out.print(
                                    "Enter Loan Type Name: ");

                            loanType.setName(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Description: ");

                            loanType.setDescription(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter Interest Rate: ");

                            loanType.setInterestRate(
                                    readDouble());

                            System.out.print(
                                    "Enter Minimum Amount: ");

                            loanType.setMinAmount(
                                    readDouble());

                            System.out.print(
                                    "Enter Maximum Amount: ");

                            loanType.setMaxAmount(
                                    readDouble());

                            System.out.print(
                                    "Enter Maximum Tenure (months): ");

                            loanType.setMaxTenureMonths(
                                    readInt());

                            loanType.setStatus(
                                    "ACTIVE");

                            loanTypeController
                                    .addLoanType(
                                            loanType);

                            System.out.println(
                                    "\nLoan Type added successfully!");

                            System.out.println(
                                    "Generated Loan Type ID: "
                                            + loanType
                                            .getLoanTypeId());

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "\nUnable to add loan type: "
                                            + e.getMessage());

                            System.out.println(
                                    "Please enter the details again.");
                        }
                    }

                    break;

                case 2:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== GET LOAN TYPE ==========");

                            System.out.print(
                                    "Enter Loan Type ID: ");

                            int id = readInt();

                            LoanType loanType =
                                    loanTypeController
                                            .getLoanTypeById(id);

                            if (loanType == null) {

                                System.out.println(
                                        "Loan Type not found.");

                                continue;
                            }

                            printLoanType(loanType);

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to retrieve loan type: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 3:

                    try {

                        System.out.println(
                                "\n========== ALL LOAN TYPES ==========");

                        List<LoanType> loanTypes =
                                loanTypeController
                                        .getAllLoanTypes();

                        if (loanTypes == null
                                || loanTypes.isEmpty()) {

                            System.out.println(
                                    "No loan types found.");

                        } else {

                            for (LoanType loanType :
                                    loanTypes) {

                                printLoanType(loanType);

                                System.out.println(
                                        "----------------------------");
                            }
                        }

                    } catch (Exception e) {

                        System.out.println(
                                "Unable to retrieve loan types: "
                                        + e.getMessage());
                    }

                    break;

                case 4:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== UPDATE LOAN TYPE ==========");

                            System.out.print(
                                    "Enter Loan Type ID: ");

                            int id = readInt();

                            LoanType loanType =
                                    loanTypeController
                                            .getLoanTypeById(id);

                            if (loanType == null) {

                                System.out.println(
                                        "Loan Type not found.");

                                continue;
                            }

                            System.out.println(
                                    "\nCurrent Loan Type Details:");

                            printLoanType(loanType);

                            System.out.print(
                                    "Enter New Name: ");

                            loanType.setName(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Description: ");

                            loanType.setDescription(
                                    sc.nextLine());

                            System.out.print(
                                    "Enter New Interest Rate: ");

                            loanType.setInterestRate(
                                    readDouble());

                            System.out.print(
                                    "Enter New Minimum Amount: ");

                            loanType.setMinAmount(
                                    readDouble());

                            System.out.print(
                                    "Enter New Maximum Amount: ");

                            loanType.setMaxAmount(
                                    readDouble());

                            System.out.print(
                                    "Enter New Maximum Tenure (months): ");

                            loanType.setMaxTenureMonths(
                                    readInt());

                            System.out.print(
                                    "Enter New Status (ACTIVE/INACTIVE): ");

                            loanType.setStatus(
                                    sc.nextLine().toUpperCase());

                            loanTypeController
                                    .updateLoanType(
                                            loanType);

                            System.out.println(
                                    "Loan Type updated successfully.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to update loan type: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 5:

                    while (true) {

                        try {

                            System.out.println(
                                    "\n========== DELETE LOAN TYPE ==========");

                            System.out.print(
                                    "Enter Loan Type ID: ");

                            int id = readInt();

                            LoanType loanType =
                                    loanTypeController
                                            .getLoanTypeById(id);

                            if (loanType == null) {

                                System.out.println(
                                        "Loan Type not found.");

                                continue;
                            }

                            printLoanType(loanType);

                            System.out.print(
                                    "Are you sure? (YES/NO): ");

                            String confirmation =
                                    sc.nextLine();

                            if (!confirmation.equalsIgnoreCase(
                                    "YES")) {

                                System.out.println(
                                        "Delete operation cancelled.");

                                break;
                            }

                            loanTypeController
                                    .deleteLoanType(id);

                            System.out.println(
                                    "Loan Type delete/deactivate operation completed.");

                            break;

                        } catch (Exception e) {

                            System.out.println(
                                    "Unable to delete loan type: "
                                            + e.getMessage());
                        }
                    }

                    break;

                case 6:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    // ================================================================
    // APPLICATION MANAGEMENT
    // ================================================================

    private static void applicationManagementMenu() {

        while (true) {

            System.out.println(
                    "\n========== LOAN APPLICATION MANAGEMENT ==========");

            System.out.println("1. View Application By ID");
            System.out.println("2. View All Applications");
            System.out.println("3. Update Application");
            System.out.println("4. Delete Application");
            System.out.println("5. Approve Application");
            System.out.println("6. Reject Application");
            System.out.println("7. Back");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    viewApplicationById();
                    break;

                case 2:
                    viewAllApplications();
                    break;

                case 3:
                    updateApplication();
                    break;

                case 4:
                    deleteApplication();
                    break;

                case 5:
                    approveApplication();
                    break;

                case 6:
                    rejectApplication();
                    break;

                case 7:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    private static void viewApplicationById() {

        while (true) {

            try {

                System.out.println(
                        "\n========== GET LOAN APPLICATION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    continue;
                }

                printApplication(application);

                break;

            } catch (Exception e) {

                System.out.println(
                        "Unable to retrieve application: "
                                + e.getMessage());
            }
        }
    }

    private static void viewAllApplications() {

        try {

            System.out.println(
                    "\n========== ALL LOAN APPLICATIONS ==========");

            List<LoanApplication> applications =
                    applicationController
                            .getAllApplications();

            if (applications == null
                    || applications.isEmpty()) {

                System.out.println(
                        "No applications found.");

                return;
            }

            for (LoanApplication application :
                    applications) {

                printApplication(application);

                System.out.println(
                        "----------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve applications: "
                            + e.getMessage());
        }
    }

    private static void approveApplication() {

        while (true) {

            try {

                System.out.println(
                        "\n========== APPROVE LOAN APPLICATION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                System.out.print(
                        "Enter Loan Officer ID: ");

                int loanOfficerId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    continue;
                }

                if (!"PENDING".equalsIgnoreCase(
                        application.getStatus())) {

                    System.out.println(
                            "Only PENDING applications can be approved.");

                    return;
                }

                printApplication(application);

                System.out.print(
                        "\nEnter Approval Remarks: ");

                String remarks =
                        sc.nextLine();

                applicationController
                        .approveApplication(
                                applicationId,
                                loanOfficerId,
                                remarks);

                System.out.println(
                        "\nLoan Application approved successfully!");

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to approve application: "
                                + e.getMessage());
            }
        }
    }

    private static void rejectApplication() {

        while (true) {

            try {

                System.out.println(
                        "\n========== REJECT LOAN APPLICATION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                System.out.print(
                        "Enter Loan Officer ID: ");

                int loanOfficerId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    continue;
                }

                if (!"PENDING".equalsIgnoreCase(
                        application.getStatus())) {

                    System.out.println(
                            "Only PENDING applications can be rejected.");

                    return;
                }

                printApplication(application);

                System.out.print(
                        "\nEnter Rejection Remarks: ");

                String remarks =
                        sc.nextLine();

                if (remarks.trim().isEmpty()) {

                    System.out.println(
                            "Rejection remarks are required.");

                    continue;
                }

                applicationController
                        .rejectApplication(
                                applicationId,
                                loanOfficerId,
                                remarks);

                System.out.println(
                        "\nLoan Application rejected successfully!");

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to reject application: "
                                + e.getMessage());
            }
        }
    }

    private static void updateApplication() {

        while (true) {

            try {

                System.out.println(
                        "\n========== UPDATE LOAN APPLICATION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    continue;
                }

                if (!"PENDING".equalsIgnoreCase(
                        application.getStatus())) {

                    System.out.println(
                            "Only PENDING applications can be updated.");

                    return;
                }

                printApplication(application);

                System.out.print(
                        "Enter New Requested Amount: ");

                application.setRequestedAmount(
                        readDouble());

                System.out.print(
                        "Enter New Tenure (months): ");

                application.setTenureMonths(
                        readInt());

                System.out.print(
                        "Enter New Purpose: ");

                application.setPurpose(
                        sc.nextLine());

                applicationController
                        .updateApplication(
                                application);

                System.out.println(
                        "Application updated successfully.");

                break;

            } catch (Exception e) {

                System.out.println(
                        "Unable to update application: "
                                + e.getMessage());
            }
        }
    }

    private static void deleteApplication() {

        while (true) {

            try {

                System.out.println(
                        "\n========== DELETE LOAN APPLICATION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    continue;
                }

                printApplication(application);

                System.out.print(
                        "Are you sure? (YES/NO): ");

                String confirmation =
                        sc.nextLine();

                if (!confirmation.equalsIgnoreCase(
                        "YES")) {

                    System.out.println(
                            "Delete operation cancelled.");

                    return;
                }

                applicationController
                        .deleteApplication(
                                applicationId);

                System.out.println(
                        "Application deleted successfully.");

                break;

            } catch (Exception e) {

                System.out.println(
                        "Unable to delete application: "
                                + e.getMessage());
            }
        }
    }

    // ================================================================
    // LOAN MANAGEMENT
    // ================================================================

    private static void loanManagementMenu() {

        while (true) {

            System.out.println(
                    "\n========== LOAN MANAGEMENT ==========");

            System.out.println("1. Create Loan");
            System.out.println("2. Get Loan By ID");
            System.out.println("3. Get All Loans");
            System.out.println("4. Update Loan");
            System.out.println("5. Delete Loan");
            System.out.println("6. Back");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    createLoan();
                    break;

                case 2:
                    getLoanById();
                    break;

                case 3:
                    getAllLoans();
                    break;

                case 4:
                    updateLoan();
                    break;

                case 5:
                    deleteLoan();
                    break;

                case 6:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
            }
        }
    }

    private static void createLoan() {

        while (true) {

            try {

                System.out.println(
                        "\n========== CREATE LOAN ==========");

                System.out.print(
                        "Enter Approved Application ID: ");

                int applicationId = readInt();

                System.out.print(
                        "Enter Loan Officer ID: ");

                int loanOfficerId = readInt();

                Loan loan = new Loan();

                loan.setApplicationId(
                        applicationId);

                loan.setCreatedBy(
                        loanOfficerId);

                loanController.addLoan(loan);

                System.out.println(
                        "\nLoan created successfully!");

                System.out.println(
                        "Generated Loan ID: "
                                + loan.getLoanId());

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to create loan: "
                                + e.getMessage());

                System.out.println(
                        "Please enter the details again.");
            }
        }
    }

    private static void getLoanById() {

        while (true) {

            try {

                System.out.println(
                        "\n========== GET LOAN ==========");

                System.out.print(
                        "Enter Loan ID: ");

                int loanId = readInt();

                Loan loan =
                        loanController
                                .getLoanById(
                                        loanId);

                if (loan == null) {

                    System.out.println(
                            "Loan not found.");

                    continue;
                }

                printLoan(loan);

                break;

            } catch (Exception e) {

                System.out.println(
                        "Unable to retrieve loan: "
                                + e.getMessage());
            }
        }
    }

    private static void getAllLoans() {

        try {

            System.out.println(
                    "\n========== ALL LOANS ==========");

            List<Loan> loans =
                    loanController.getAllLoans();

            if (loans == null || loans.isEmpty()) {

                System.out.println(
                        "No loans found.");

                return;
            }

            for (Loan loan : loans) {

                printLoan(loan);

                System.out.println(
                        "----------------------------");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve loans: "
                            + e.getMessage());
        }
    }

    private static void updateLoan() {

        while (true) {

            try {

                System.out.println(
                        "\n========== UPDATE LOAN ==========");

                System.out.print(
                        "Enter Loan ID: ");

                int loanId = readInt();

                Loan loan =
                        loanController
                                .getLoanById(
                                        loanId);

                if (loan == null) {

                    System.out.println(
                            "Loan not found.");

                    continue;
                }

                printLoan(loan);

                System.out.print(
                        "Enter New Principal Amount: ");

                loan.setPrincipalAmount(
                        readDouble());

                System.out.print(
                        "Enter New Interest Rate: ");

                loan.setInterestRate(
                        readDouble());

                System.out.print(
                        "Enter New Tenure (months): ");

                loan.setTenureMonths(
                        readInt());

                System.out.print(
                        "Enter New Total Payable: ");

                loan.setTotalPayable(
                        readDouble());

                System.out.print(
                        "Enter New Outstanding Amount: ");

                loan.setOutstandingAmount(
                        readDouble());

                System.out.print(
                        "Enter New Status (ACTIVE/CLOSED): ");

                loan.setStatus(
                        sc.nextLine().toUpperCase());

                loanController.updateLoan(loan);

                System.out.println(
                        "\nLoan updated successfully!");

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to update loan: "
                                + e.getMessage());
            }
        }
    }

    private static void deleteLoan() {

        while (true) {

            try {

                System.out.println(
                        "\n========== DELETE LOAN ==========");

                System.out.print(
                        "Enter Loan ID: ");

                int loanId = readInt();

                Loan loan =
                        loanController
                                .getLoanById(
                                        loanId);

                if (loan == null) {

                    System.out.println(
                            "Loan not found.");

                    continue;
                }

                printLoan(loan);

                System.out.print(
                        "Are you sure? (YES/NO): ");

                String confirmation =
                        sc.nextLine();

                if (!confirmation.equalsIgnoreCase(
                        "YES")) {

                    System.out.println(
                            "Delete operation cancelled.");

                    return;
                }

                loanController.deleteLoan(
                        loanId);

                System.out.println(
                        "Loan deleted successfully.");

                break;

            } catch (Exception e) {

                System.out.println(
                        "Unable to delete loan: "
                                + e.getMessage());
            }
        }
    }

    // ================================================================
    // OFFICER REVIEW
    // ================================================================

    private static void officerReviewApplication() {

        while (true) {

            try {

                System.out.println(
                        "\n========== LOAN APPROVAL / REJECTION ==========");

                System.out.print(
                        "Enter Application ID: ");

                int applicationId = readInt();

                System.out.print(
                        "Enter Loan Officer ID: ");

                int loanOfficerId = readInt();

                LoanApplication application =
                        applicationController
                                .getApplicationById(
                                        applicationId);

                if (application == null) {

                    System.out.println(
                            "Application not found.");

                    return;
                }

                if (!"PENDING".equalsIgnoreCase(
                        application.getStatus())) {

                    System.out.println(
                            "Only PENDING applications can be reviewed.");

                    return;
                }

                printApplication(application);

                System.out.println(
                        "\n1. Approve Application");

                System.out.println(
                        "2. Reject Application");

                System.out.print(
                        "Enter choice: ");

                int decision = readInt();

                System.out.print(
                        "Enter Remarks: ");

                String remarks =
                        sc.nextLine();

                if (decision == 1) {

                    applicationController
                            .approveApplication(
                                    applicationId,
                                    loanOfficerId,
                                    remarks);

                    System.out.println(
                            "\nApplication approved successfully.");

                } else if (decision == 2) {

                    if (remarks.trim().isEmpty()) {

                        System.out.println(
                                "Rejection remarks are required.");

                        return;
                    }

                    applicationController
                            .rejectApplication(
                                    applicationId,
                                    loanOfficerId,
                                    remarks);

                    System.out.println(
                            "\nApplication rejected successfully.");

                } else {

                    System.out.println(
                            "Invalid choice. Please select 1 or 2.");
                }

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to process application: "
                                + e.getMessage());
            }
        }
    }

    // ================================================================
    // CUSTOMER FUNCTIONS
    // ================================================================

    private static void viewCustomerProfile(
            String username) {

        try {

            Customer customer =
                    customerController
                            .getCustomerByUsername(
                                    username);

            if (customer == null) {

                System.out.println(
                        "Customer profile not found.");

                return;
            }

            System.out.println(
                    "\n========== MY PROFILE ==========");

            printCustomer(customer);

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve profile: "
                            + e.getMessage());
        }
    }

    private static void updateCustomerProfile(
            String username) {

        try {

            Customer customer =
                    customerController
                            .getCustomerByUsername(
                                    username);

            if (customer == null) {

                System.out.println(
                        "Customer profile not found.");

                return;
            }

            System.out.println(
                    "\n========== UPDATE MY PROFILE ==========");

            System.out.print(
                    "Enter New Full Name: ");

            customer.setFullName(
                    sc.nextLine());

            System.out.print(
                    "Enter New Email: ");

            customer.setEmail(
                    sc.nextLine());

            System.out.print(
                    "Enter New Phone: ");

            customer.setPhone(
                    sc.nextLine());

            System.out.print(
                    "Enter New Address: ");

            customer.setAddress(
                    sc.nextLine());

            System.out.print(
                    "Enter New Monthly Income: ");

            customer.setMonthlyIncome(
                    readDouble());

            customerController
                    .updateCustomer(
                            customer);

            System.out.println(
                    "Profile updated successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Unable to update profile: "
                            + e.getMessage());
        }
    }

    private static void viewAvailableLoanTypes() {

        try {

            System.out.println(
                    "\n========== AVAILABLE LOAN TYPES ==========");

            List<LoanType> loanTypes =
                    loanTypeController
                            .getAllLoanTypes();

            boolean found = false;

            if (loanTypes != null) {

                for (LoanType loanType :
                        loanTypes) {

                    if ("ACTIVE".equalsIgnoreCase(
                            loanType.getStatus())) {

                        printLoanType(loanType);

                        System.out.println(
                                "----------------------------");

                        found = true;
                    }
                }
            }

            if (!found) {

                System.out.println(
                        "No active loan types available.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve loan types: "
                            + e.getMessage());
        }
    }

    private static void applyForLoan(
            int customerId) {

        while (true) {

            try {

                System.out.println(
                        "\n========== APPLY FOR LOAN ==========");

                System.out.println(
                        "Customer ID: "
                                + customerId);

                System.out.print(
                        "Enter Loan Type ID: ");

                int loanTypeId = readInt();

                System.out.print(
                        "Enter Requested Amount: ");

                double amount = readDouble();

                System.out.print(
                        "Enter Tenure (months): ");

                int tenure = readInt();

                System.out.print(
                        "Enter Purpose: ");

                String purpose =
                        sc.nextLine();

                LoanApplication application =
                        new LoanApplication();

                application.setCustomerId(
                        customerId);

                application.setLoanTypeId(
                        loanTypeId);

                application.setRequestedAmount(
                        amount);

                application.setTenureMonths(
                        tenure);

                application.setPurpose(
                        purpose);

                applicationController
                        .addApplication(
                                application);

                System.out.println(
                        "\nLoan application submitted successfully!");

                System.out.println(
                        "Application ID: "
                                + application
                                .getApplicationId());

                System.out.println(
                        "Status: "
                                + application.getStatus());

                break;

            } catch (Exception e) {

                System.out.println(
                        "\nUnable to submit application: "
                                + e.getMessage());

                System.out.println(
                        "Please enter the application details again.");
            }
        }
    }

    private static void viewMyApplications(
            int customerId) {

        try {

            System.out.println(
                    "\n========== MY APPLICATIONS ==========");

            List<LoanApplication> applications =
                    applicationController
                            .getAllApplications();

            boolean found = false;

            if (applications != null) {

                for (LoanApplication application :
                        applications) {

                    if (application.getCustomerId()
                            == customerId) {

                        printApplication(
                                application);

                        System.out.println(
                                "----------------------------");

                        found = true;
                    }
                }
            }

            if (!found) {

                System.out.println(
                        "No applications found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve applications: "
                            + e.getMessage());
        }
    }

    private static void viewMyLoans(
            int customerId) {

        try {

            System.out.println(
                    "\n========== MY LOANS ==========");

            List<Loan> loans =
                    loanController.getAllLoans();

            boolean found = false;

            if (loans != null) {

                for (Loan loan :
                        loans) {

                    if (loan.getCustomerId()
                            == customerId) {

                        printLoan(loan);

                        System.out.println(
                                "----------------------------");

                        found = true;
                    }
                }
            }

            if (!found) {

                System.out.println(
                        "No loans found.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve loans: "
                            + e.getMessage());
        }
    }

    private static void changeCustomerPassword(
            String username) {

        try {

            User user =
                    userController
                            .getUserByUsername(
                                    username);

            if (user == null) {

                System.out.println(
                        "User not found.");

                return;
            }

            System.out.print(
                    "Enter New Password: ");

            String newPassword =
                    sc.nextLine();

            user.setPassword(
                    newPassword);

            userController.updateUser(
                    user);

            System.out.println(
                    "Password updated successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Unable to change password: "
                            + e.getMessage());
        }
    }

    // ================================================================
    // PRINT CUSTOMER
    // ================================================================

    private static void printCustomer(
            Customer customer) {

        System.out.println(
                "Customer ID       : "
                        + customer.getCustomerId());

        System.out.println(
                "User ID           : "
                        + customer.getUserId());

        System.out.println(
                "Full Name         : "
                        + customer.getFullName());

        System.out.println(
                "Email             : "
                        + customer.getEmail());

        System.out.println(
                "Phone             : "
                        + customer.getPhone());

        System.out.println(
                "DOB               : "
                        + customer.getDob());

        System.out.println(
                "Address           : "
                        + customer.getAddress());

        System.out.println(
                "Monthly Income    : "
                        + customer.getMonthlyIncome());

        System.out.println(
                "PAN Number        : "
                        + customer.getPanNumber());

        System.out.println(
                "Aadhaar Last 4    : "
                        + customer.getAadhaarLast4());

        System.out.println(
                "Employment Type   : "
                        + customer.getEmploymentType());

        System.out.println(
                "Account Number    : "
                        + customer.getAccountNumber());

        System.out.println(
                "IFSC Code         : "
                        + customer.getIfscCode());

        System.out.println(
                "Bank Name         : "
                        + customer.getBankName());

        System.out.println(
                "KYC Status        : "
                        + customer.getKycStatus());

        System.out.println(
                "KYC Remarks       : "
                        + customer.getKycRemarks());

        System.out.println(
                "Credit Score      : "
                        + customer.getCreditScore());

        System.out.println(
                "Existing EMI      : "
                        + customer.getExistingEmi());

        System.out.println(
                "Status            : "
                        + customer.getStatus());
    }

    // ================================================================
    // PRINT LOAN TYPE
    // ================================================================

    private static void printLoanType(
            LoanType loanType) {

        System.out.println(
                "Loan Type ID       : "
                        + loanType.getLoanTypeId());

        System.out.println(
                "Name               : "
                        + loanType.getName());

        System.out.println(
                "Description        : "
                        + loanType.getDescription());

        System.out.println(
                "Interest Rate      : "
                        + loanType.getInterestRate());

        System.out.println(
                "Minimum Amount     : "
                        + loanType.getMinAmount());

        System.out.println(
                "Maximum Amount     : "
                        + loanType.getMaxAmount());

        System.out.println(
                "Maximum Tenure     : "
                        + loanType.getMaxTenureMonths()
                        + " months");

        System.out.println(
                "Status             : "
                        + loanType.getStatus());
    }

    // ================================================================
    // PRINT APPLICATION
    // ================================================================

    private static void printApplication(
            LoanApplication application) {

        System.out.println(
                "Application ID   : "
                        + application.getApplicationId());

        System.out.println(
                "Customer ID      : "
                        + application.getCustomerId());

        System.out.println(
                "Loan Type ID     : "
                        + application.getLoanTypeId());

        System.out.println(
                "Requested Amount : "
                        + application.getRequestedAmount());

        System.out.println(
                "Tenure           : "
                        + application.getTenureMonths()
                        + " months");

        System.out.println(
                "Purpose          : "
                        + application.getPurpose());

        System.out.println(
                "Status           : "
                        + application.getStatus());

        System.out.println(
                "Remarks          : "
                        + application.getRemarks());

        System.out.println(
                "Reviewed By      : "
                        + application.getReviewedBy());

        System.out.println(
                "Applied At       : "
                        + application.getAppliedAt());

        System.out.println(
                "Reviewed At      : "
                        + application.getReviewedAt());
    }

    // ================================================================
    // PRINT LOAN
    // ================================================================

    private static void printLoan(
            Loan loan) {

        System.out.println(
                "Loan ID            : "
                        + loan.getLoanId());

        System.out.println(
                "Application ID     : "
                        + loan.getApplicationId());

        System.out.println(
                "Customer ID        : "
                        + loan.getCustomerId());

        System.out.println(
                "Loan Type ID       : "
                        + loan.getLoanTypeId());

        System.out.println(
                "Principal Amount   : "
                        + loan.getPrincipalAmount());

        System.out.println(
                "Interest Rate      : "
                        + loan.getInterestRate());

        System.out.println(
                "Tenure             : "
                        + loan.getTenureMonths()
                        + " months");

        System.out.println(
                "Total Payable      : "
                        + loan.getTotalPayable());

        System.out.println(
                "Outstanding Amount : "
                        + loan.getOutstandingAmount());

        System.out.println(
                "Start Date         : "
                        + loan.getStartDate());

        System.out.println(
                "Status             : "
                        + loan.getStatus());

        System.out.println(
                "Created By         : "
                        + loan.getCreatedBy());
    }

    // ================================================================
    // INPUT HELPERS
    // ================================================================

    private static int readInt() {

        while (true) {

            String input =
                    sc.nextLine().trim();

            try {

                return Integer.parseInt(
                        input);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid number. Enter again: ");
            }
        }
    }

    private static double readDouble() {

        while (true) {

            String input =
                    sc.nextLine().trim();

            try {

                return Double.parseDouble(
                        input);

            } catch (NumberFormatException e) {

                System.out.print(
                        "Invalid number. Enter again: ");
            }
        }
    }
}








