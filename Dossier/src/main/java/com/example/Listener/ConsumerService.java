package com.example.Listener;

import com.example.Dto.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class ConsumerService {

    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private final SendEmail sender;

    public ConsumerService(SendEmail sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = "finish-registration")
    public void consumeFinishRegistration(@Payload EmailMessage message) {

        log.info("Получено сообщение: {}", message);
        sender.sendMail(message);
        log.info("Топик FinishRegistration отработал");

    }

    @KafkaListener(topics = "send-documents")
    public void consumeSendDocuments(@Payload EmailMessage message) {

        log.info("Получено сообщение: {}", message);
        sender.sendMail(message);
        log.info("Топик send-documents отработал");
    }

    @KafkaListener(topics = "create-documents")
    public void consumeCreateDocuments(@Payload EmailMessage message) {

        log.info("Received message: {}", message);
        sender.sendMail(message);
        log.info("Топик create-documents отработал");

    }

    @KafkaListener(topics = "send-ses")
    public void consumeSesCode(@Payload EmailMessage message) {

        log.info("Received message: {}", message);
        sender.sendMail(message);
        log.info("Топик send-ses отработал");

    }
    @KafkaListener(topics = "credit-issued")
    public void consumeCreditIssued(@Payload EmailMessage message) {

        log.info("Received message: {}", message);
        sender.sendMail(message);
        log.info("Топик credit-issued отработал");

    }
}