package com.example;

import com.example.Dto.*;
import com.example.entity.Client;
import com.example.entity.Credit;
import com.example.entity.Statement;
import com.example.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    public List<LoanOfferDto> correctListLoanOfferDto(){

        LoanOfferDto loanOfferDto1 = LoanOfferDto.builder()
                .statementId(1L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_276_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(212_666.67))
                .rate(BigDecimal.valueOf(16))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        LoanOfferDto loanOfferDto2 = LoanOfferDto.builder()
                .statementId(2L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_287_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(214_500))
                .rate(BigDecimal.valueOf(17))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        LoanOfferDto loanOfferDto3 = LoanOfferDto.builder()
                .statementId(3L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_190_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(198_333.33))
                .rate(BigDecimal.valueOf(19))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        LoanOfferDto loanOfferDto4 = LoanOfferDto.builder()
                .statementId(4L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_190_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(198_333.33))
                .rate(BigDecimal.valueOf(20))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        List<LoanOfferDto> list = new ArrayList<>();
        list.add(loanOfferDto4);
        list.add(loanOfferDto3);
        list.add(loanOfferDto2);
        list.add(loanOfferDto1);
        return list;
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

    public Client createClientEntity() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("TestName");
        client.setLastName("TestLastName");
        client.setMiddleName("TestMiddleName");
        client.setBirthdate(LocalDate.of(1990, 1, 1));
        client.setEmail("test@example.com");
        Passport passport = new Passport();
        passport.setSeries("1234");
        passport.setNumber("567890");
        client.setPassport(passport);
        return client;
    }

    public Credit createCreditEntity() {
        Credit credit = new Credit();
        credit.setId(1L);
        credit.setAmount(BigDecimal.valueOf(100000));
        credit.setTerm(12);
        return credit;
    }

    public Statement createStatementEntity() {
        Statement statement = new Statement();
        statement.setId(1L);
        return statement;
    }


    }
