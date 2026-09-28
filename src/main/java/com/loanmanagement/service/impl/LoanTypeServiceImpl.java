package com.loanmanagement.service.impl;

import com.loanmanagement.dao.LoanApplicationDao;
import com.loanmanagement.dao.LoanTypeDao;
import com.loanmanagement.dao.impl.LoanApplicationDaoImpl;
import com.loanmanagement.dao.impl.LoanTypeDaoImpl;
import com.loanmanagement.exception.NotFoundException;
import com.loanmanagement.exception.ValidationException;
import com.loanmanagement.model.LoanType;
import com.loanmanagement.service.LoanTypeService;

import java.util.List;

public class LoanTypeServiceImpl implements LoanTypeService {

    private LoanTypeDao loanTypeDao = new LoanTypeDaoImpl();
    private LoanApplicationDao loanApplicationDao =
            new LoanApplicationDaoImpl();
    @Override
    public void addLoanType(LoanType loanType) {
        if (loanType.getName() == null ||
                loanType.getName().trim().isEmpty()) {
            throw new ValidationException("Loan type name is required");
        }
        if (loanType.getMinAmount() < 0) {
            throw new ValidationException(
                    "Minimum amount cannot be negative");
        }

        if (loanType.getMaxAmount() <= 0) {
            throw new ValidationException(
                    "Maximum amount must be greater than zero");
        }

        if (loanType.getMinAmount() > loanType.getMaxAmount()) {
            throw new ValidationException(
                    "Minimum amount cannot be greater than maximum amount");
        }

        if (loanType.getInterestRate() < 0) {
            throw new ValidationException(
                    "Interest rate cannot be negative");
        }

        if (loanType.getMaxTenureMonths() <= 0) {
            throw new ValidationException(
                    "Maximum tenure must be greater than zero");
        }

        loanTypeDao.addLoanType(loanType);
    }

    @Override
    public LoanType getLoanTypeById(int loanTypeId) {
        return loanTypeDao.getLoanTypeById(loanTypeId);
    }

    @Override
    public void updateLoanType(LoanType loanType) {
        if (loanType.getMinAmount() < 0) {
            throw new ValidationException(
                    "Minimum amount cannot be negative");
        }

        if (loanType.getMaxAmount() <= 0) {
            throw new ValidationException(
                    "Maximum amount must be greater than zero");
        }

        if (loanType.getMinAmount() > loanType.getMaxAmount()) {
            throw new ValidationException(
                    "Minimum amount cannot be greater than maximum amount");
        }

        if (loanType.getInterestRate() < 0) {
            throw new ValidationException(
                    "Interest rate cannot be negative");
        }

        if (loanType.getMaxTenureMonths() <= 0) {
            throw new ValidationException(
                    "Maximum tenure must be greater than zero");
        }
        if (loanType.getStatus() == null ||
                (!"ACTIVE".equals(loanType.getStatus())
                        && !"INACTIVE".equals(loanType.getStatus()))) {
            throw new ValidationException("Invalid loan type status");
        }
        loanTypeDao.updateLoanType(loanType);
    }

    @Override
    public void deleteLoanType(int loanTypeId) {


        LoanType loanType = loanTypeDao.getLoanTypeById(loanTypeId);

        if (loanType == null) {
            throw new NotFoundException("Loan type not found");
        }

        if (loanApplicationDao.existsByLoanTypeId(loanTypeId)) {
            loanType.setStatus("INACTIVE");
            loanTypeDao.updateLoanType(loanType);
            return;
        }

        loanTypeDao.deleteLoanType(loanTypeId);

    }
    @Override
    public List<LoanType> getAllLoanTypes() {
        return loanTypeDao.getAllLoanTypes();
    }
}
