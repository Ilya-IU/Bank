package com.example.config;


import com.example.Dto.EmailMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendService {

    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public SendService(KafkaTemplate<String, EmailMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendDoc(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("send-documents", message);
    }
    public void sendFinish(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("finish-registration", message);
    }
    public void sendCreate(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("create-documents", message);
    }
    public void sendSes(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("send-ses", message);
    }
    public void sendCredit(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("credit-issued", message);
    }
    public void sendStatement(EmailMessage message) throws InterruptedException {
        kafkaTemplate.send("statement-denied", message);
    }
}