package com.example.Calculate.dto;

import com.example.Calculate.services.interfaces.CalculatePreScoring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoanOfferTest {

    @Mock
    private CalculatePreScoring calculatePreScoring;

    @Test
    public void testCalculateBodyCredit() {
        double insuranceCost = 0.1;
        boolean isInsuranceEnabled = true;
        BigDecimal requestedAmount = BigDecimal.valueOf(100_000);
        BigDecimal expectedBodyCredit = BigDecimal.valueOf(110_000);

        when(calculatePreScoring.calculateBodyCredit(requestedAmount, insuranceCost, isInsuranceEnabled))
                .thenReturn(expectedBodyCredit);
        BigDecimal actualBodyCredit = calculatePreScoring.calculateBodyCredit(requestedAmount,insuranceCost,isInsuranceEnabled);

        assertEquals(expectedBodyCredit.doubleValue(), actualBodyCredit.doubleValue());
    }

    @Test
    public void testCalculateRate() {
        double baseRate = 0.2;
        double insurancePersent = 0.03;
        double salaryPersent = 0.01;
        boolean isInsuranceEnabled = true;
        boolean isSalaryEnabled = true;
        BigDecimal expectedRate = BigDecimal.valueOf(0.16);

        when(calculatePreScoring.calculateRate(baseRate, insurancePersent, salaryPersent, isInsuranceEnabled, isSalaryEnabled))
                .thenReturn(expectedRate);
        BigDecimal actualRate = calculatePreScoring.calculateRate(baseRate, insurancePersent, salaryPersent, isInsuranceEnabled, isSalaryEnabled);

        assertEquals(expectedRate, actualRate);
    }

    @Test
    public void testCalculateTotalAmount() {
        BigDecimal bodyCredit = BigDecimal.valueOf(110_000);
        BigDecimal rate = BigDecimal.valueOf(0.16);
        BigDecimal expectedTotalAmount = BigDecimal.valueOf(127600);

        when(calculatePreScoring.calculateTotalAmount(bodyCredit, rate)).thenReturn(expectedTotalAmount);
        BigDecimal actualTotalAmount = calculatePreScoring.calculateTotalAmount(bodyCredit, rate);

        assertEquals(expectedTotalAmount.doubleValue(), actualTotalAmount.doubleValue());
    }

    @Test
    public void testMonthlyPayment() {
        LoanOfferDto loanOfferDto = new LoanOfferDto();
        BigDecimal totalAmount = BigDecimal.valueOf(126000);
        int term = 10;
        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(12600);

        when(calculatePreScoring.monthlyPayment(totalAmount, term)).thenReturn(expectedMonthlyPayment);
        BigDecimal actualMonthlyPayment = calculatePreScoring.monthlyPayment(totalAmount, term);

        assertEquals(expectedMonthlyPayment, actualMonthlyPayment);
    }

}