package com.example.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {

    private String series;
    private String number;
    private LocalDate issueDate;
    private String issueBranch;
}