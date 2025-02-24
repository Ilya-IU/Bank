package com.example;

import com.example.Dto.*;
import com.example.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Dtos {

    public ScoringDataDto correctScoringData() {

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100_000))
                .term(10)
                .firstName("Vasya")
                .middleName("Vasilevich")
                .lastName("Vasilev")
                .gender(Gender.MAN)
                .birthdate(LocalDate.of(2000, 1, 1))
                .passportSeries("123456")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.of(2023, 1, 1))
                .passportIssueBranch("gdsfgfd")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(100_000)
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .employerINN("123456789")
                        .salary(BigDecimal.valueOf(10_000))
                        .workExperienceCurrent(5)
                        .workExperienceTotal(25)
                        .position(Position.MIDDLE_MANAGER)
                        .build())
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        return scoringDataDto;
    }


    public LoanOfferDto correctLoanOfferDto(){

        LoanOfferDto loanOfferDto1 = LoanOfferDto.builder()
                .statementId(1L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_160_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(193_333.33))
                .rate(BigDecimal.valueOf(16))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        return loanOfferDto1;
    }

    public EmailMessage correctEmailMessage(){
        EmailMessage emailMessage = EmailMessage.builder()
                .statementId(1L)
                .address("Test@gmail.com")
                .theme(Theme.SEND_DOCUMENTS)
                .build();
        return emailMessage;
    }


    public FinishRegistrationRequestDto correctFinishRegistrationRequestDto(){

        FinishRegistrationRequestDto finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .gender(Gender.MAN)
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(25)
                .passportIssueDate(LocalDate.parse("2024-11-28"))
                .passportIssueBranch("214312")
                .employment(EmploymentDto.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .salary(BigDecimal.valueOf(300000))
                        .position(Position.MIDDLE_MANAGER)
                        .workExperienceTotal(26)
                        .workExperienceCurrent(5)
                        .build())
                .build();
        return finishRegistrationRequestDto;
    }

    public LoanStatementRequestDto correctLoanStatementRequest() {

        LoanStatementRequestDto loanStatementRequestDto =
                LoanStatementRequestDto.builder()
                        .amount(BigDecimal.valueOf(1_000_000))
                        .term(10)
                        .firstName("Vasiliy")
                        .lastName("Vasilev")
                        .middleName("Vasilevich")
                        .email("VVVasilev@gmail.ru")
                        .birthdate(LocalDate.of(2000, 1, 1))
                        .passportSeries("1234")
                        .passportNumber("123456")
                        .build();
        return loanStatementRequestDto;
    }

    }
