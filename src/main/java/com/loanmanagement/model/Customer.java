package com.loanmanagement.model;

public class Customer {
    private int customerId;
    private int userId;
    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private String address;
    private double monthlyIncome;
    private String panNumber;
    private String aadhaarLast4;
    private String employmentType;
    private String accountNumber;
    private String ifscCode;
    private String bankName;
    private KycStatus kycStatus;
    private String kycRemarks;
    private int kycVerifiedBy;
    private String kycVerifiedAt;
    private int creditScore;
    private double existingEmi;
    private CustomerStatus status;

    public Customer(int customerId, int userId, String fullName, String email, String phone, String dob, String address,
                    double monthlyIncome, String panNumber, String aadhaarLast4, String employmentType,
                    String accountNumber, String ifscCode, String bankName, KycStatus kycStatus, String kycRemarks,
                    int kycVerifiedBy, String kycVerifiedAt, int creditScore, double existingEmi, CustomerStatus status) {
        this.customerId = customerId;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.monthlyIncome = monthlyIncome;
        this.panNumber = panNumber;
        this.aadhaarLast4 = aadhaarLast4;
        this.employmentType = employmentType;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.bankName = bankName;
        this.kycStatus = kycStatus;
        this.kycRemarks = kycRemarks;
        this.kycVerifiedBy = kycVerifiedBy;
        this.kycVerifiedAt = kycVerifiedAt;
        this.creditScore = creditScore;
        this.existingEmi = existingEmi;
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getAadhaarLast4() {
        return aadhaarLast4;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getBankName() {
        return bankName;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public String getKycRemarks() {
        return kycRemarks;
    }

    public int getKycVerifiedBy() {
        return kycVerifiedBy;
    }

    public String getKycVerifiedAt() {
        return kycVerifiedAt;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public double getExistingEmi() {
        return existingEmi;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public void setAadhaarLast4(String aadhaarLast4) {
        this.aadhaarLast4 = aadhaarLast4;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }

    public void setKycRemarks(String kycRemarks) {
        this.kycRemarks = kycRemarks;
    }

    public void setKycVerifiedBy(int kycVerifiedBy) {
        this.kycVerifiedBy = kycVerifiedBy;
    }

    public void setKycVerifiedAt(String kycVerifiedAt) {
        this.kycVerifiedAt = kycVerifiedAt;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public void setExistingEmi(double existingEmi) {
        this.existingEmi = existingEmi;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}
