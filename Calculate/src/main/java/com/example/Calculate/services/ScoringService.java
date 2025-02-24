package com.example.Calculate.services;

import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.PaymentScheduleElementDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.interfaces.CalculateScoring;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;



@Slf4j
@Service
@NoArgsConstructor
public class ScoringService{
    @Autowired
    private CalculateScoring calculateScoring;

    @Value("${variables.insurancePersent}")
    private double insurancePersent;

    @Value("${variables.salaryPersent}")
    private double salaryPersent;

    @Value("${variables.baseRate}")
    private double baseRate;

    public CreditDto calculateCreditParams(ScoringDataDto request) throws ScoringExeption {

        try {
            log.info("Запуск скоринга");
            calculateScoring.scoringApplication(request);
            log.info("Скоринг заявки завершен");
            log.info("Расчет параметров кредита");

            double finalRate = calculateScoring.calculateRate(request, salaryPersent, insurancePersent) + baseRate;
            log.info("Конечная ставка  = {}", finalRate);

            BigDecimal totalAmount = calculateScoring.calculateTotalAmount(request.getIsInsuranceEnabled(),  request.getAmount(), finalRate);
            log.info("Расчет первоначальной суммы кредита = {}", totalAmount);

            BigDecimal monthRate = BigDecimal.valueOf(finalRate).divide(BigDecimal.valueOf(12), 15, RoundingMode.HALF_EVEN);
            log.info("Расчет месячной ставки = {}", monthRate);

            BigDecimal koeffAnuetet = calculateScoring.calcelateKoeffAnyetet(monthRate, request.getTerm());
            log.info("Расчет коэффициента ануетета = {}", koeffAnuetet);

            BigDecimal monthyPayment = calculateScoring.calculateMonthPayment(totalAmount, monthRate, request.getTerm(), koeffAnuetet);
            log.info("Расчет месячного платежа = {}", monthyPayment);

            log.info("Расчет окончательной суммы кредита = {}", totalAmount);
            List<PaymentScheduleElementDto> paymentSchedule = calculateScoring.calculatePaymentScheduleElements(request.getTerm(), monthRate,
                    totalAmount, monthyPayment);
            log.info("Формирвоание списка платежей = {}", paymentSchedule.size());

            BigDecimal psk = calculateScoring.calculatePsk(request.getAmount(), totalAmount, request.getTerm());
            log.info("Расчитан ПСК = {}", psk);

            return new CreditDto(totalAmount.setScale(2, RoundingMode.HALF_EVEN), request.getTerm(),
                    monthyPayment.setScale(2, RoundingMode.HALF_EVEN),
                    BigDecimal.valueOf(finalRate).setScale(2, BigDecimal.ROUND_HALF_EVEN),
                    psk, request.getIsInsuranceEnabled(), request.getIsSalaryClient(), paymentSchedule);
        }
        catch (ScoringExeption ex) {
            log.debug("Ошибка при расчете параметров кредита "+ex.getMessage());
            throw new ScoringExeption(ex.getMessage());
        }
    }
}


































    //Расчет месячной ставки и ануитетного платежа, полной стоимости кредита, размер ежмес платежа, расписание платежей

    //Формула расчета аннуитетного платежа
    //Х = С * К
    //где X — аннуитетный платеж,
    //С — сумма кредита,
    //К — коэффициент аннуитета.
    //Коэффициент аннуитета считается так:
    //К = (М * (1 + М) ^ S) / ((1 + М) ^ S — 1)
    //где М — месячная процентная ставка по кредиту,
    //S — срок кредита в месяцах.
    //М = ГС/100/СК
    // ГС - годовая ставка double
    // СК - срок кредита в месяцах
    //Допустим, вы берете в кредит 2 млн ₽ на 5 лет по ставке 15% годовых. Сначала подсчитаем коэффициент аннуитета.
    //К = (0,0125 * (1 + 0,0125) ^ 60) / ((1 + 0,0125) ^ 60 — 1)
    //Получаем коэффициент 0,02379.
    //Подставляем значения в формулу:
    //Х = 2 000 000 * К


