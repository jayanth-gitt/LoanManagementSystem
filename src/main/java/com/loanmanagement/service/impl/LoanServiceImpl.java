package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanDao;
import com.loanmanagement.dao.LoanTypeDao;

import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;

import com.loanmanagement.model.Loan;
import com.loanmanagement.model.LoanApplication;
import com.loanmanagement.model.LoanType;

import com.loanmanagement.service.LoanService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanServiceImpl implements LoanService {

    private final LoanDao loanDao = new LoanDaoImpl();

    private final LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();

    private final LoanTypeDao loanTypeDao =
            new LoanTypeDaoImpl();

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public int addLoan(Loan loan) {

        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }

        // 1. Get the related loan application
        LoanApplication application =
                loanApplicationDao.getLoanApplicationById(
                        loan.getApplicationId()
                );

        if (application == null) {
            throw new IllegalArgumentException(
                    "Loan application not found"
            );
        }

        // 2. Only APPROVED applications can create loans
        if (!"APPROVED".equalsIgnoreCase(application.getStatus())) {
            throw new IllegalStateException(
                    "Loan can be created only for an APPROVED application"
            );
        }

        // 3. Get the loan type
        LoanType loanType =
                loanTypeDao.getLoanTypeById(
                        application.getLoanTypeId()
                );

        if (loanType == null) {
            throw new IllegalArgumentException(
                    "Loan type not found"
            );
        }

        // 4. Loan type must be ACTIVE
        if (!"ACTIVE".equalsIgnoreCase(loanType.getStatus())) {
            throw new IllegalStateException(
                    "Inactive loan type cannot be used"
            );
        }

        // 5. Get values from approved application
        double principalAmount =
                application.getRequestedAmount();

        int tenureMonths =
                application.getTenureMonths();

        double interestRate =
                loanType.getInterestRate();

        // 6. Validate amount
        if (principalAmount < loanType.getMinAmount()
                || principalAmount > loanType.getMaxAmount()) {

            throw new IllegalArgumentException(
                    "Requested amount is outside the allowed loan range"
            );
        }

        // 7. Validate tenure
        if (tenureMonths <= 0
                || tenureMonths > loanType.getMaxTenureMonths()) {

            throw new IllegalArgumentException(
                    "Invalid loan tenure"
            );
        }

        // 8. Calculate EMI
        double monthlyRate =
                interestRate / 12 / 100;

        double emiAmount;

        if (monthlyRate == 0) {

            emiAmount =
                    principalAmount / tenureMonths;

        } else {

            double power =
                    Math.pow(
                            1 + monthlyRate,
                            tenureMonths
                    );

            emiAmount =
                    principalAmount
                            * monthlyRate
                            * power
                            / (power - 1);
        }

        // Round EMI to 2 decimal places
        emiAmount =
                Math.round(emiAmount * 100.0) / 100.0;

        // 9. Calculate start date
        LocalDate startDate =
                LocalDate.now();

        // 10. Calculate end date
        LocalDate endDate =
                startDate.plusMonths(tenureMonths);

        // 11. Set all loan values
        loan.setApplicationId(
                application.getApplicationId()
        );

        loan.setCustomerId(
                application.getCustomerId()
        );

        loan.setLoanTypeId(
                application.getLoanTypeId()
        );

        loan.setPrincipalAmount(
                principalAmount
        );

        // Interest rate is frozen at loan creation
        loan.setInterestRate(
                interestRate
        );

        loan.setTenureMonths(
                tenureMonths
        );

        loan.setEmiAmount(
                emiAmount
        );

        loan.setStartDate(
                startDate.format(DATE_FORMATTER)
        );

        loan.setEndDate(
                endDate.format(DATE_FORMATTER)
        );

        // Outstanding starts with principal
        loan.setOutstandingAmount(
                principalAmount
        );

        // New loan starts as ACTIVE
        loan.setStatus("ACTIVE");

        // 12. Insert loan
        return loanDao.addLoan(loan);
    }

    @Override
    public Loan getLoanById(int loanId) {
        return loanDao.getLoanById(loanId);
    }

    @Override
    public int updateLoan(Loan loan) {

        if (loan == null) {
            throw new IllegalArgumentException(
                    "Loan cannot be null"
            );
        }

        return loanDao.updateLoan(loan);
    }

    @Override
    public int deleteLoan(int loanId) {
        return loanDao.deleteLoan(loanId);
    }
}