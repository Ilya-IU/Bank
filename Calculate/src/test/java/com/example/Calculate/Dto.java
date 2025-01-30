package com.example.Calculate;


import com.example.Calculate.dto.EmploymentDto;
import com.example.Calculate.dto.LoanOfferDto;
import com.example.Calculate.dto.LoanStatementRequestDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.enums.EmploymentStatus;
import com.example.Calculate.enums.Gender;
import com.example.Calculate.enums.MaritalStatus;
import com.example.Calculate.enums.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dto {


    public List<LoanOfferDto> correctSortListLoanOffer() {


        List<LoanOfferDto> loanOfferDtoForTest = new ArrayList<>();

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
        LoanOfferDto loanOfferDto2 = LoanOfferDto.builder()
                .statementId(2L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_170_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(195_000))
                .rate(BigDecimal.valueOf(17))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        LoanOfferDto loanOfferDto3 = LoanOfferDto.builder()
                .statementId(3L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_190_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(197_333.33))
                .rate(BigDecimal.valueOf(19))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        LoanOfferDto loanOfferDto4 = LoanOfferDto.builder()
                .statementId(4L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_200_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(200_000))
                .rate(BigDecimal.valueOf(20))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
        loanOfferDtoForTest.add(loanOfferDto1);
        loanOfferDtoForTest.add(loanOfferDto2);
        loanOfferDtoForTest.add(loanOfferDto3);
        loanOfferDtoForTest.add(loanOfferDto4);
        return loanOfferDtoForTest;
    }
    public List<LoanOfferDto> notCorrectSortListLoanOffer() {


        List<LoanOfferDto> loanOfferDtoForTest = new ArrayList<>();

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
        LoanOfferDto loanOfferDto2 = LoanOfferDto.builder()
                .statementId(2L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_170_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(195_000))
                .rate(BigDecimal.valueOf(17))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        LoanOfferDto loanOfferDto3 = LoanOfferDto.builder()
                .statementId(3L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_190_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(197_333.33))
                .rate(BigDecimal.valueOf(19))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        LoanOfferDto loanOfferDto4 = LoanOfferDto.builder()
                .statementId(4L)
                .requestedAmount(BigDecimal.valueOf(1_000_000))
                .totalAmount(BigDecimal.valueOf(1_200_000))
                .term(6)
                .monthlyPayment(BigDecimal.valueOf(200_000))
                .rate(BigDecimal.valueOf(20))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
        loanOfferDtoForTest.add(loanOfferDto4);
        loanOfferDtoForTest.add(loanOfferDto2);
        loanOfferDtoForTest.add(loanOfferDto1);
        loanOfferDtoForTest.add(loanOfferDto3);
        return loanOfferDtoForTest;
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
        LoanStatementRequestDto loanStatementRequestDtoInvalidData =
                LoanStatementRequestDto.builder()
                        .amount(BigDecimal.valueOf(1_000_000))
                        .term(1)
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .middleName(null)
                        .email("Ivanov@gmail.ru")
                        .birthdate(LocalDate.of(2000, 2, 2))
                        .passportSeries("4321")
                        .passportNumber("654321")
                        .build();
        return loanStatementRequestDtoInvalidData;
    }

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
    public ScoringDataDto notCorrectScoringData() {
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
                        .workExperienceCurrent(1)
                        .workExperienceTotal(2)
                        .position(Position.MIDDLE_MANAGER)
                        .build())
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        return scoringDataDto;
    }
}

//}
