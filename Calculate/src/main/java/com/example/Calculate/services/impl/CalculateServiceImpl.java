package com.example.Calculate.services.impl;

import com.example.Calculate.dto.*;
import com.example.Calculate.exeptions.PreScorExeption;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.interfaces.CalculateService;
import com.example.Calculate.services.ScoringService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@NoArgsConstructor
public class CalculateServiceImpl implements CalculateService, Comparator<LoanOfferDto> {
    @Autowired
    private ScoringService scoringService;

    @Value("${variables.baseRate}")
    private double baseRate;

    @Value("${variables.insuranceCost}")
    private double insuranceCost;

    @Value("${variables.insurancePersent}")
    private double insurancePersent;

    @Value("${variables.salaryPersent}")
    private double salaryPersent;



    //todo code first contract first read

    @Override
    public List<LoanOfferDto> getOffers(LoanStatementRequestDto request) throws PreScorExeption {
        log.info("Вызов метода по формированию списка предложений");
        List<LoanOfferDto> listLoanOfferDto = new ArrayList<>();
        try {
            log.info("Добавление предложений в список");
            listLoanOfferDto.add(new LoanOfferDto(1L,request.getAmount(), baseRate, salaryPersent, insurancePersent,
                    insuranceCost, request.getTerm(), true, true));
            log.info("Добавлено первое предложение = {}", listLoanOfferDto.get(0));
            listLoanOfferDto.add(new LoanOfferDto(2L,request.getAmount(), baseRate, salaryPersent, insurancePersent,
                    insuranceCost, request.getTerm(), true, false));
            log.info("Добавлено второе предложение");
            listLoanOfferDto.add(new LoanOfferDto(3L,request.getAmount(), baseRate, salaryPersent, insurancePersent,
                    insuranceCost, request.getTerm(), false, true));
            log.info("Добавлено третье предложение");
            listLoanOfferDto.add(new LoanOfferDto(4L,request.getAmount(), baseRate, salaryPersent, insurancePersent,
                    insuranceCost, request.getTerm(), false, false));

            log.info("Сортировка выполнена");
        } catch (Exception e) {
            log.error("Ошибка прескоринга: " + e.getMessage());
            throw new PreScorExeption(e.getMessage());

        }
        log.info("Метод по формированию списка предложений отработал успешно");
        try {
            log.info("Список сформирован/Выполняется сортировка");
            Collections.sort(listLoanOfferDto, new RateComparator() {
            });
        }
        catch (Exception e){
            log.error("Ошибка на этапе сортировки списка");
            throw new PreScorExeption(e.getMessage());
        }
        return listLoanOfferDto;

    }

    @Override
    public CreditDto calculateCreditParams(ScoringDataDto request) {
        log.info("Вызов метода по расчету параметров кредита");
        try {
            return scoringService.calculateCreditParams(request);
        } catch (Exception e) {
            log.error("Ошибка скоринга: " + e.getMessage());
            throw new ScoringExeption(e.getMessage());

        }
    }

    @Override
    public int compare(LoanOfferDto o1, LoanOfferDto o2) {
        return 0;
    }

    class RateComparator implements Comparator<LoanOfferDto> {
        @Override
        public int compare(LoanOfferDto object1, LoanOfferDto object2) {
            return object2.getRate().compareTo(object1.getRate());
        }
    }

}
