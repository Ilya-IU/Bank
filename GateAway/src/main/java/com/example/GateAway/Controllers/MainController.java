package com.example.GateAway.Controllers;


import com.example.GateAway.Dto.EmailMessage;
import com.example.GateAway.Dto.FinishRegistrationRequestDto;
import com.example.GateAway.Dto.LoanOfferDto;
import com.example.GateAway.Dto.LoanStatementRequestDto;
import com.example.GateAway.FeignClient.DealClient;
import com.example.GateAway.exeptions.FeignClientExeption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/gateAway")
@Slf4j
public class MainController {

    @Autowired
    private DealClient dealClient;

    @PostMapping(value = "/preOffers")
    public List<LoanOfferDto> getPreOffers(@RequestBody LoanStatementRequestDto requestDto) throws FeignClientExeption {
        log.info("Получение предварительных предложений");

        return dealClient.getOffers(requestDto);

    }
    @PostMapping(value = "/updateStatus")
    public void selectStatement(@RequestBody LoanOfferDto requestDto) throws FeignClientExeption{
        log.info("Обновление статуса заявки");
        dealClient.selectStatusHistory(requestDto);

    }
    @PostMapping(value = "/finishReg/{statementId}")
    public void finishRegistration(@RequestBody FinishRegistrationRequestDto requestDto, @PathVariable Long statementId)throws FeignClientExeption{
        log.info("Завершение регистрации пользователя");
        dealClient.FinishRegistrationAndCredit(requestDto, statementId);
    }

    @PostMapping(value = "document/sendDoc/{statementId}")
    public String sendDoc(@RequestBody EmailMessage emailMessage, @PathVariable Long statementId) throws FeignClientExeption{
        log.info("Отправка документов на почту клиента");
        log.info("statementId: " + statementId);
        dealClient.sendDocuments(emailMessage, statementId);
        return "Пакет документов для ознакомления отправлен на почту " + emailMessage.getAddress();
    }
    @PostMapping(value = "document/sendSes/{statementId}")
    public String sendSesCode(@RequestBody EmailMessage emailMessage, @PathVariable Long statementId) throws FeignClientExeption{
        log.info("Отправка кода для подписи документов на почту клиента");
        dealClient.sendSes(emailMessage);
        return "Код для подтверждения был отправлен на вашу почту " + emailMessage.getAddress();
    }
    @PostMapping(value = "document/sendCredit/{statementId}")
    public String sendCredit(@RequestBody EmailMessage emailMessage, @PathVariable Long statementId) throws FeignClientExeption{
        log.info("Завершение оформления кредита");
        dealClient.sendCreditdIssued(emailMessage);
        return "Кредитное предложение оформлено, подписанный комплект документов направлен на почту " + emailMessage.getAddress();
    }


}
