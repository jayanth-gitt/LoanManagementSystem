package com.loanmanagement.util;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null &&
                phone.matches("\\d{10}");
    }
}
