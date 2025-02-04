package com.example.Statement.controllers;

import com.example.Statement.Dto.LoanOfferDto;
import com.example.Statement.Dto.LoanStatementRequestDto;
import com.example.Statement.exceptions.FeignClientExeption;
import com.example.Statement.feignClient.DealClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/statement")
@Slf4j
@Tag(name = "Контроллер для отправки запросов в DealService",
        description = "Расчитываются кредитные предложения и сохраняются данные клиента и заявки в БД. ")
public class MainController {

    @Autowired
    DealClient dealClient;


    @PostMapping
    public List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto requestDto) throws FeignClientExeption {
        log.info("Запуск метода FeignClient/deal для получения 4-х кредитных предложений");
        try {

            return dealClient.getOffers(requestDto);
        }
        catch (FeignClientExeption e) {
            throw new FeignClientExeption("Ошибка FeignClient/Deal");
        }
    }

    @PostMapping(value = "/offer")
    public void selectStatusHistory(@RequestBody LoanOfferDto requestDto) throws Exception {
        log.info("Запуск метода FeignClient/deal для изменения статуса заявки с параметрами Request: {}", requestDto);
        try {

            dealClient.updateStatusHistory(requestDto);
        }
        catch (Exception e) {
            throw new FeignClientExeption("При обновлении статуса заявки произошла ошибка");
        }
    }


}