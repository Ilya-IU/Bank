package com.example.Calculate.services;

import com.example.Calculate.Dto;
import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.LoanOfferDto;
import com.example.Calculate.dto.LoanStatementRequestDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.exeptions.PreScorExeption;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.impl.CalculateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateServiceImplTest {
    Dto dto;

    @Mock
    CalculateServiceImpl service;


    @BeforeEach
    void setUp() {
        dto = new Dto();
    }

    @Test
    void getOffers_Valid() {

        LoanStatementRequestDto request = dto.correctLoanStatementRequest();

        when(service.getOffers(request)).thenReturn(new ArrayList<LoanOfferDto>());
        assertEquals(new ArrayList<LoanOfferDto>(), service.getOffers(request));
    }
    @Test
    void getOffers_NotValid() throws PreScorExeption {

        LoanStatementRequestDto request = dto.notCorrectLoanStatementRequest();

        when(service.getOffers(request)).thenThrow(PreScorExeption.class);
        assertThrows(PreScorExeption.class, () -> service.getOffers(request));
    }


    @Test
    void calculateCreditParams_Valid() {
        ScoringDataDto request = dto.correctScoringData();

        when(service.calculateCreditParams(request)).thenReturn(new CreditDto());
        assertEquals(new CreditDto(), service.calculateCreditParams(request));
    }

    @Test
    void calculateCreditParams_NotValid() throws ScoringExeption {
        ScoringDataDto request = dto.notCorrectScoringData();

        when(service.calculateCreditParams(request)).thenThrow(ScoringExeption.class);
        assertThrows(ScoringExeption.class, () -> service.calculateCreditParams(request));
    }

//    @Test
//    void compare() {
//
//    }
}