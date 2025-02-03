package com.example.controllers;

import com.example.Dto.EmailMessage;
import com.example.Dto.FinishRegistrationRequestDto;
import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;
import com.example.entity.Statement;
import com.example.exeptions.FeignClientExeption;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.services.AdminService;
import com.example.services.interfaces.CalculateScoringService;
import com.example.services.KafkaService;
import com.example.services.interfaces.StatementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/deal")
@Slf4j
@Tag(name = "Контроллер для расчетов предложений и  формирования сущностей в БД",
        description = "Расчитываются кредитные предложения и сохраняются данные клиента и заявки в БД. ")
public class MainController {

    @Autowired
    private StatementService statementService;

    @Autowired
    private CalculateScoringService calculateScoringService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    AdminService adminService;

    @PostMapping(value = "/statement")
    public List<LoanOfferDto> getOffers(@RequestBody LoanStatementRequestDto requestDto) throws FeignClientExeption {

        return statementService.getListOffers(requestDto);
    }


    @PostMapping(value = "/offer/select")
    public String selectStatusHistory(@RequestBody LoanOfferDto requestDto) throws Exception {
        log.info("Вызов метода изменения статуса заявки с параметрами Request: {}", requestDto);
        statementService.updateStatusOffer(requestDto);
        log.info("Предложение обработано/Вызов сервиса по отправка сообщений");
        kafkaService.finishRegistration(requestDto.getStatementId());
        log.info("Сообщение отправлено в Dossier");
        return "Успешно";
    }

    @PostMapping(value = "/calculate/{statementId}")
    public void FinishRegistrationAndCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                            @PathVariable Long statementId) throws FeignClientExeption, NotFoundStatementEntityByid {
        log.info("Запущен сервис скоринга с параметрами finishRegistrationRequestDto: {}, statementId = {}",
                finishRegistrationRequestDto, statementId);
        calculateScoringService.scoringCalculateOffer(finishRegistrationRequestDto, statementId);
        kafkaService.createDocuments(statementId);
    }

    @PostMapping(value = "/document/{statementId}/send")
    public String sendDocuments(@RequestBody EmailMessage message, @PathVariable Long statementId) throws Exception {

        kafkaService.sendDocuments(message.getStatementId());
        return "Сообщение отправлено, sendCreateDocuments";
    }

    @PostMapping(value = "/document/{statementId}/sign")
    public String sendSes(@RequestBody EmailMessage message) throws Exception {
        log.info("Controler_message = {}", message.toString());

        kafkaService.sendSes(message.getStatementId());
        return "Сообщение отправлено, SendDocuments";
    }

    @PostMapping(value = "/document/{statementId}/code")
    public String sendCreditdIssued(@RequestBody EmailMessage message) throws Exception {

        kafkaService.sendCreditIssued(message.getStatementId());
        return "Сообщение отправлено, sendSesCode";
    }

    @GetMapping(value = "/admin/statement/{Id}")
    public Statement getStatement(@PathVariable String Id) throws Exception {
        return adminService.getStatementById(Id);
    }

    @GetMapping(value = "/admin/statement")
    public List<Statement> ListAllStatements() throws SQLException {
        log.info("Запрос на получение всех заявок из БД");
        return adminService.getAllStatements();
    }


}
