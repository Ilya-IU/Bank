package com.example.Calculate.services.impl;

import com.example.Calculate.dto.PaymentScheduleElementDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.enums.EmploymentStatus;
import com.example.Calculate.enums.Gender;
import com.example.Calculate.enums.MaritalStatus;
import com.example.Calculate.enums.Position;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.interfaces.CalculateScoring;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.between;

@Slf4j
@Component
public class CalculateScoringImpl implements CalculateScoring {

    @Value("${variables.baseRate}")
    private double baseRate;

    @Value("${variables.insuranceCost}")
    private double insuranceCost;

    @Override
    public boolean scoringApplication(ScoringDataDto scoringDataDto) throws ScoringExeption {
        boolean flag = true;
        log.info("Проверка правил скоринга");
        long age = scoringDataDto.getBirthdate().until(LocalDate.now(), ChronoUnit.YEARS);

        if (age > 65 || age < 20) {
            flag = false;
            log.info("В кредите отказано, в связи с  возрастом клиента age = {}", age);
            throw new ScoringExeption("В кредите отказано, в связи с вашим возрастом");

        }
        if (scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.UNEMPLOYED) {
            log.info("В кредите отказано, потому что клиент безработный getEmploymentStatus() = {}",
                    scoringDataDto.getEmployment().getEmploymentStatus());
            flag = false;
            throw new ScoringExeption("В кредите отказано, потому что клиент безработный");
        }
        if ( BigDecimal.valueOf(25.0).multiply(scoringDataDto.getEmployment().getSalary()).doubleValue() <= scoringDataDto.getAmount().doubleValue()) {
            log.info("Сумма кредита превышает 25 ЗП клиента ЗП = {}, сумма кредита = {}",scoringDataDto.getEmployment().getSalary(),scoringDataDto.getAmount());
            flag = false;
            throw new ScoringExeption("В кредите отказано, потому что сумма кредита " +
                    "превышает 25 заработных плат клиента");
        }

        if (scoringDataDto.getEmployment().getWorkExperienceTotal() <= 18 &&
                scoringDataDto.getEmployment().getWorkExperienceCurrent() <= 3) {
            log.info("Отказ в связи с опытом работы WorkExperienceTotal = {}, WorkExperienceCurrent = {}",
                    scoringDataDto.getEmployment().getWorkExperienceTotal(),
                    scoringDataDto.getEmployment().getWorkExperienceCurrent());
            flag = false;
            throw new ScoringExeption("В кредите отказано, потому что для получения кредита необходимо " +
                    "иметь общий стаж минимум 18 месяцев, " +
                    "а на текущем месте работы не менее 3-х месяцев");
        }

        return flag;
    }

    @Override
    public double calculateRate(ScoringDataDto scoringDataDto, double salaryPersent, double insurancePersent) throws ScoringExeption {

        double rate =0;

        long age = scoringDataDto.getBirthdate().until(LocalDate.now(), ChronoUnit.YEARS);
        log.info("Проверка правил скоринга завершена, ошибок нет/Запускается расчет ставки");
        if (scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.SELF_EMPLOYED) {
            log.info("К ставке +1 из-за статуса клиента = {}", scoringDataDto.getEmployment().getEmploymentStatus());
            rate = rate+1;
        }
        if (scoringDataDto.getEmployment().getEmploymentStatus() == EmploymentStatus.BUSINESS_OWNER) {
            log.info("К ставке +2 из-за статуса клиента = {}", scoringDataDto.getEmployment().getEmploymentStatus());
            rate = rate+2;
        }
        if (scoringDataDto.getIsInsuranceEnabled()) {
            log.info("К ставке -3 из-за включенной страховки клиента = {}", scoringDataDto.getIsInsuranceEnabled());
            rate = rate - insurancePersent*100;
        }
        if (scoringDataDto.getIsSalaryClient()) {
            log.info("К ставке -1 из-за зарплатного клиента = {}", scoringDataDto.getIsSalaryClient());
            rate = rate - salaryPersent*100;
        }
        if (scoringDataDto.getEmployment().getPosition() == Position.MIDDLE_MANAGER) {
            log.info("К ставке -2 из-за должности = {}", scoringDataDto.getEmployment().getPosition());
            rate = rate-2;
        }
        if (scoringDataDto.getEmployment().getPosition() == Position.TOP_MANAGER) {
            log.info("К ставке -3 из-за должности = {}", scoringDataDto.getEmployment().getPosition());
            rate = rate - 3;
        }
        if (scoringDataDto.getMaritalStatus() == MaritalStatus.DIVORCED) {
            log.info("К ставке +1 из-за разведенного статуса клиента = {}", scoringDataDto.getMaritalStatus());
            rate = rate + 1;
        }
        if (scoringDataDto.getMaritalStatus() == MaritalStatus.MARRIED) {
            log.info("К ставке -3 из-за брака клиента = {}", scoringDataDto.getMaritalStatus());
            rate = rate - 3;
        }
        if ((scoringDataDto.getGender() == Gender.WOMAN) && (age >= 32 && age <= 60)) {
            log.info("К ставке -3 из-за пола клиента = {} и возраста = {} ", scoringDataDto.getGender(), age);
            rate = rate - 3;
        }
        if ((scoringDataDto.getGender() == Gender.MAN) && (age >= 30 && age <= 55)) {
            log.info("К ставке -3 из-за пола клиента = {} и возраста = {} ", scoringDataDto.getGender(), age);
            rate = rate -3;
        }
        if (scoringDataDto.getGender() == Gender.UNKNOWN) {
            log.info("К ставке +7 из-за пола клиента = {} ", scoringDataDto.getGender());
            rate = rate+7;
        }
        log.info("Промежуточная ставка расчитана rate = {}", rate);

        rate = rate/100;
        return rate;
    }

    @Override
    public BigDecimal calcelateKoeffAnyetet(BigDecimal rate, int term){
        log.info("Расчет коэфициента ануетета");
        BigDecimal koefAn= rate.add(rate.divide((((rate.add(BigDecimal.ONE)).pow(term+1)).subtract(BigDecimal.ONE)),15, RoundingMode.HALF_EVEN)) ;
        log.info("Расчитан коэффициент ануета koeffAn = {}", koefAn);
        return koefAn;

    }

    @Override
    public BigDecimal calculateMonthPayment(BigDecimal preTotalAmount, BigDecimal monthRate, int term, BigDecimal koefAnuetet) {

        log.info("Расчет месячного платежа с параметрами: koefAnuetet = {}, totalAmount = {}",
                koefAnuetet, preTotalAmount);
        log.info("Месячный платеж = сумма кредита со страховкой * на коеф ануетета");

        BigDecimal monthPayment = preTotalAmount.setScale(4,RoundingMode.FLOOR).multiply(koefAnuetet.setScale(2,RoundingMode.CEILING));

        log.info("Месячный платеж расчитнан monthPayment = {}", monthPayment);

        return monthPayment;
    }


    @Override
    public BigDecimal calculateTotalAmount(boolean isInsurance,  BigDecimal requestedAmount, double rate) {

        log.info("Расчет всей суммы кредита с параметрами: isInsurance = {},  requestedAmount = {}",
                isInsurance,   requestedAmount);
        log.info("сумму кредита  = считаем как сумма кредита* на годовую ставку + сумма кредита + цена страховки");
        BigDecimal insuranceCost = requestedAmount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal preTotalAmount = isInsurance ? requestedAmount.add(insuranceCost): requestedAmount;
        //log.info("Промежуточная сумма = {}", preTotalAmount);
        BigDecimal totalAmount = (requestedAmount.multiply(BigDecimal.valueOf(rate))).add(preTotalAmount) ;

        log.info("Сумма кредита со страховкой(без) посчитана totalAmount = {}", totalAmount);

        return totalAmount;
    }

    @Override
    public List<PaymentScheduleElementDto> calculatePaymentScheduleElements(int term, BigDecimal monthRate, BigDecimal totalAmount,
                                                                            BigDecimal monthyPayment) {
        log.info("Формирование списка платежей с параметрами: term = {}, monthRate = {}, totalAmount = {}, monthyPayment = {}",
                term, monthRate, totalAmount, monthyPayment);

        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();

        log.info("Добавление первого элемента в список платежей/Запуск цикла по расчету остальных платежей");
        for (int i = 0; i < term ; i++) {

            BigDecimal totalPayment = null;
            BigDecimal interestPayment = null;
            BigDecimal debtPayment = null;
            BigDecimal remainingDebt  = null;

            log.info("---------------------------------------------------------------------------");
            int number = i+1;
            log.info("Расчет номера платежа = {}", number);
            LocalDate date = LocalDate.now().plusMonths(number);
            log.info("Расчитана дата платежа = {}", date);
            totalPayment = monthyPayment;
            log.info("Расчитан месячный платежа (равен месячному платежу) = {}", totalPayment);
            log.info("Параметры расчета totalAmount = {}, totalPayment = {}, monthRate = {}", totalAmount, totalPayment, monthyPayment);
            interestPayment = (totalAmount.subtract(totalPayment.multiply(BigDecimal.valueOf(number)))).multiply(monthRate);
            log.info("Расчитаны проценты платеже под номером = {} и равны interestPayment = {}", number, interestPayment);
            log.info("Параметры расчета totalPayment = {}, interestPayment = {}", totalAmount, interestPayment);
            debtPayment = totalPayment.subtract(interestPayment);
            log.info("Расчитан основной долг в платеже = {}, debtPayment = {}", number, debtPayment);
           log.info("Расчет с параметрами totalAmount = {}, totalPayment = {}, number = {}", totalAmount, totalPayment, number);
            remainingDebt = totalAmount.subtract(totalPayment.multiply(BigDecimal.valueOf(number)));
            log.info("Расчитан остаток на месяц под номером = {}, remainingDebt = {}", number, remainingDebt);
            log.info("-----------------------------------------------------------------------------");

            paymentSchedule.add(new PaymentScheduleElementDto(number, date, totalPayment.setScale(2, RoundingMode.HALF_EVEN),
                    interestPayment.setScale(2, RoundingMode.HALF_EVEN), debtPayment.setScale(2, RoundingMode.HALF_EVEN),
                    remainingDebt.setScale(2, RoundingMode.HALF_EVEN)));

        }
        log.info("Размер списка платежей paymentSchedule = {}", paymentSchedule.size());
        return paymentSchedule;
    }

    @Override
    public BigDecimal calculatePsk(BigDecimal requestedAmount, BigDecimal totalAmount, int term) {
        BigDecimal finalTerm = BigDecimal.valueOf(term).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_EVEN);
        BigDecimal chislitel = (totalAmount.divide(requestedAmount, 10, RoundingMode.HALF_EVEN)).subtract(BigDecimal.ONE);
        BigDecimal psk = (chislitel.divide(finalTerm, 10, RoundingMode.HALF_EVEN)).multiply(BigDecimal.valueOf(100));
        return psk.setScale(2, RoundingMode.HALF_EVEN);
    }
}
