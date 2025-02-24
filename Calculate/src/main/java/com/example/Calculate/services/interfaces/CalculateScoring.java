package com.example.Calculate.services.interfaces;

import com.example.Calculate.dto.PaymentScheduleElementDto;
import com.example.Calculate.dto.ScoringDataDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface CalculateScoring {

    boolean scoringApplication(ScoringDataDto scoringDataDto);

    double calculateRate(ScoringDataDto scoringDataDto, double salaryPersent, double insurancePersent);

    BigDecimal calculatePsk(BigDecimal requestedAmount, BigDecimal totalAmount, int term);

    BigDecimal calcelateKoeffAnyetet(BigDecimal monthRate, int term);

    BigDecimal calculateTotalAmount(boolean isInsurance, BigDecimal requestedAmount, double rate);

    BigDecimal calculateMonthPayment(BigDecimal totalAmount, BigDecimal monthRate, int term, BigDecimal koefAnuetet);

    List<PaymentScheduleElementDto> calculatePaymentScheduleElements(int term, BigDecimal monthRate, BigDecimal totalAmount,
                                                                     BigDecimal monthyPayment);
}
