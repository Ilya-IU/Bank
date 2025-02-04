package com.example.Statement;

import com.example.Statement.Dto.LoanOfferDto;
import com.example.Statement.Dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Dtos {



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
    public LoanStatementRequestDto notCorrectLoanStatementRequest() {

        LoanStatementRequestDto loanStatementRequestDto =
                LoanStatementRequestDto.builder()
                        .amount(BigDecimal.valueOf(1_000_000))
                        .term(2)
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