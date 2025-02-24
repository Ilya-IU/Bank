package com.example.Calculate.services.interfaces;

import java.math.BigDecimal;

public interface CalculatePreScoring {
    BigDecimal calculateBodyCredit(BigDecimal requestedAmount, double insurancePersent, boolean isInsuranceEnabled);
    BigDecimal calculateRate(double baseRate,double insurancePersent, double salaryPersent,boolean isInsuranceEnabled,boolean isSalaryClient);
    BigDecimal calculateTotalAmount(BigDecimal bodyCredit, BigDecimal rate);
    BigDecimal monthlyPayment(BigDecimal totalAmount, int term);
}
