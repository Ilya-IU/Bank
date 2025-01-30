package com.example.Calculate.controllers;

import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.LoanOfferDto;
import com.example.Calculate.dto.LoanStatementRequestDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.exeptions.PreScorExeption;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.interfaces.CalculateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/calculate")
@Tag(name = "Контроллер для расчета кредитного предложения",
        description = "Расчитываются первоначальные кредитные предложения, " +
                "а также расчитывается подробный вариант для выдачи кредита.")
public class MainController {


    private CalculateService calculateService;

    @Autowired
    public MainController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @Operation(
            summary = "Принимается заявка от пользователя для получения кредита",
            description = "Пользователь получает четыре кредитных предложения, " +
                    "в порядке убывания годовой ставки.")

    @PostMapping(value = "/offer")
    public List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto requestDto) throws PreScorExeption {

        return calculateService.getOffers(requestDto);
    }

    @Operation(
            summary = "Окончательный расчет кредита для пользователя",
            description = "Пользователь получает полную стоимость кредита, годовую ставку и график платежей.")

    @PostMapping(value = "/calc")
    public CreditDto calculateCreditParams(@Valid @RequestBody ScoringDataDto requestDto) throws ScoringExeption {

        return calculateService.calculateCreditParams(requestDto);


    }


}
