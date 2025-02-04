package com.example.GateAway.FeignClient;

import com.example.GateAway.Dto.EmailMessage;
import com.example.GateAway.Dto.FinishRegistrationRequestDto;
import com.example.GateAway.Dto.LoanOfferDto;
import com.example.GateAway.Dto.LoanStatementRequestDto;
import com.example.GateAway.exeptions.CustomErrorDecoder;
import com.example.GateAway.exeptions.FeignClientExeption;
import com.example.GateAway.exeptions.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "http://localhost:9091",  configuration = {FeignConfig.class, CustomErrorDecoder.class})
public interface DealClient {
    //Расчет 4х предложений
    @PostMapping(value = "/deal/statement")
    List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto requestDto) throws FeignClientExeption;

    //Обновление статуса заявки
    @PostMapping(value = "/deal/offer/select")
    void selectStatusHistory(@RequestBody LoanOfferDto requestDto);

    //Отправка пиьсма + заполнение бд
    @PostMapping(value = "/deal/calculate/{statementId}")
    void FinishRegistrationAndCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                            @PathVariable Long statementId);
    //Отправка письма с документами
    @PostMapping(value = "/deal/document/{statementId}/send")
    String sendDocuments(@RequestBody EmailMessage message, @PathVariable Long statementId);

    //Отправка пиcьма с кодом для подписи
    @PostMapping(value = "/deal/document/{statementId}/sign")
    String sendSes(@RequestBody EmailMessage message);

    //Отправка письма о завершении
    @PostMapping(value = "/deal/document/{statementId}/code")
    String sendCreditdIssued(@RequestBody EmailMessage message);
}
