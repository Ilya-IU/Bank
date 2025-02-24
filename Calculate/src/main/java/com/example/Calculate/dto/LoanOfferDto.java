package com.example.Calculate.dto;

import com.example.Calculate.services.interfaces.CalculatePreScoring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class LoanOfferDto implements CalculatePreScoring {


    private Long statementId;
    private BigDecimal requestedAmount; //запрошенная сумма клиентом 100_000_0
    private BigDecimal totalAmount;     // сумма с процентами + страховка
    private Integer term;               //срок 10 мес
    private BigDecimal monthlyPayment; //еж мес платеж
    private BigDecimal rate;            // 20% ставка
    private Boolean isInsuranceEnabled; // 10% от суммы кредита -3% от ставки
    private Boolean isSalaryClient;     // -1% от ставки

    public LoanOfferDto(Long id, BigDecimal requestedAmount, double baseRate, double salaryPersent,
                 double insurancePersent, double insuranceCost, int term,
                        boolean isInsuranceEnabled, boolean isSalaryClient) {
        log.info(" ");
        log.info(" ");
        log.info("Вызов конструктора/Расчет данных предложения");
        this.statementId = id;
        this.requestedAmount = requestedAmount;
        this.term = term;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        this.rate= calculateRate(baseRate, insurancePersent, salaryPersent,isInsuranceEnabled, isSalaryClient);
        this.totalAmount=calculateTotalAmount(calculateBodyCredit(requestedAmount, insuranceCost,isInsuranceEnabled), this.rate);
        this.monthlyPayment = monthlyPayment(totalAmount, this.term);

        log.info("Расчет окончен/Предложение сформировано");
    }

    @Override
    public BigDecimal calculateBodyCredit(BigDecimal requestedAmount, double insuranceCost, boolean isInsuranceEnabled) {

        log.info("Расчет bodyCredit, с параметрами:isInsuranceEnabled = {} requestedAmount = {} " +
                "insuranceCost = {}", isInsuranceEnabled, requestedAmount, insuranceCost);

        BigDecimal bodyCredit = this.isInsuranceEnabled ? BigDecimal.valueOf(insuranceCost).multiply(requestedAmount)
                .add(requestedAmount):requestedAmount;
        log.info("Формула расчета insuranceCost + requestedAmount либо requestedAmount;");
        log.info("Расчитан параметр bodyCredit = {}", bodyCredit);
        return bodyCredit.setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public BigDecimal calculateRate(double baseRate,double insurancePersent, double salaryPersent,boolean isInsuranceEnabled,boolean isSalaryClient) {
        log.info("Расчет rate, с параметрами: isSalaryClient = {}, isInsuranceEnabledbaseRate = {}, " +
                "insurancePersent = {}, salaryPersent = {}", isSalaryClient, isInsuranceEnabled,
                insurancePersent, salaryPersent);

        double rate = this.isInsuranceEnabled ? baseRate-insurancePersent:baseRate;
        rate = this.isSalaryClient ? rate-salaryPersent:rate;

        log.info("Формула расчета baseRate - salaryPersent(если значение true) - insurancePersent(если значение true)");
        log.info("Расчитан параметр rate = {}", rate);

        return BigDecimal.valueOf(rate).setScale(2,BigDecimal.ROUND_HALF_EVEN);

    }

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal bodyCredit, BigDecimal rate) {
        log.info("Расчет totalAmount, с параметрами: bodyCredit = {}, rate = {}", bodyCredit, rate);
        BigDecimal totalAmount = bodyCredit.add((rate.multiply(bodyCredit)));
        log.info("Расчитан параметр totalAmount = {}", totalAmount);
        return totalAmount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public BigDecimal monthlyPayment(BigDecimal totalAmount, int term) {
        log.info("Расчет monthyPayment, с параметрами: totalAmount = {}, term = {}", totalAmount, term);
        BigDecimal monthPayment = totalAmount.divide(BigDecimal.valueOf(term), 2,RoundingMode.HALF_EVEN);
        log.info("Формула расчета monthPayment = totalAmount/term");
        log.info("Расчитан параметр monthPayment = {}", monthPayment);
        return monthPayment.setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }
}



