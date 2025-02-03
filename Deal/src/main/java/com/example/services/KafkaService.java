package com.example.services;

import com.example.Dto.EmailMessage;
import com.example.entity.Client;
import com.example.entity.Statement;
import com.example.enums.Theme;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.repository.StatementRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Slf4j
public class KafkaService {
    @Autowired
    StatementRepository statementRepository;

    @Autowired
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public void finishRegistration(Long statementId) throws Exception {
        log.info("Формирование сообщения для топика finish_Registration");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.FINISH_REGISTRATION)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("finish-registration", emailMessage);
    }
    public void createDocuments(Long statementId) {
        log.info("Формирование сообщения для топика create-documents");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.CREATE_DOCUMENTS)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("create-documents", emailMessage);
    }
    public void sendDocuments(Long statementId) {
        log.info("Формирование сообщения для топика send-documents");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new)
                ;
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.SEND_DOCUMENTS)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("send-documents", emailMessage);
    }
    public void sendSes(Long statementId) {
        log.info("Формирование сообщения для топика send-ses");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        Random r = new Random();
        int sesCode = r.nextInt(9999);
        statement.setSesCode(sesCode+"");
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.SEND_SES)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("send-ses", emailMessage);
    }
    public void sendCreditIssued(Long statementId) {
        log.info("Формирование сообщения для топика credit-issued");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.CREDIT_ISSUED)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("credit-issued", emailMessage);
    }
    public void sendStatementDenied(Long statementId) {
        log.info("Формирование сообщения для топика statement-denied");
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(statement.getId())
                .address(statement.getClient().getEmail())
                .theme(Theme.STATEMENT_DENIED)
                .build();
        log.info("Сообщение сформировано {}",emailMessage.toString());
        kafkaTemplate.send("statement-denied", emailMessage);
    }
}
