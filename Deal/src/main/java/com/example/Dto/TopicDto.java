package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    private String nameFinish= "finish-registration";
    private String nameCreate = "create-documents";
    private String nameSend = "send-documents";
    private String nameSendSes = "send-ses";
    private String nameCreditIssued="credit-issued";
    private String nameStatementDenied ="statement-denied";

}
