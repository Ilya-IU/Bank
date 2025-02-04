package com.example.Listener;

import com.example.Dto.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component

public class SendEmail {
    private static final Logger log = LoggerFactory.getLogger(SendEmail.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(EmailMessage message) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(message.getAddress());
        switch (message.getTheme()) {
            case FINISH_REGISTRATION:
                msg.setSubject(message.getTheme().toString());
                msg.setText("Завершите оформление кредитного предложения");
                break;
            case CREATE_DOCUMENTS:
                msg.setSubject(message.getTheme().toString());
                msg.setText("Перейдите к оформления документов");
                break;
            case SEND_DOCUMENTS:
                msg.setSubject(message.getTheme().toString());
                msg.setText("Вам отправлен пакет сформированных документов");
                break;
            case SEND_SES:
                msg.setSubject(message.getTheme().toString());
                msg.setText("Подпишите полученные документы");
                break;
            case CREDIT_ISSUED:
                msg.setSubject(message.getTheme().toString());
                msg.setText("Кредитное предложение сформировано");
                break;

        }
        log.info("Письмо ушло = {}",msg.toString());
        javaMailSender.send(msg);
    }
}