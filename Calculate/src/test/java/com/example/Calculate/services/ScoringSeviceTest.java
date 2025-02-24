package com.example.Calculate.services;

import com.example.Calculate.Dto;
import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.exeptions.ScoringExeption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoringSeviceTest {

    Dto dto;

    @Mock
    ScoringService scoringService;

    @BeforeEach
    void setUp() {
        dto = new Dto();
    }


    @Test
    public void calcilateCreditPararams_Valid(){

        ScoringDataDto request = dto.correctScoringData();

        when(scoringService.calculateCreditParams(request)).thenReturn(new CreditDto());
        assertTrue(scoringService.calculateCreditParams(request).equals(new CreditDto()));
    }

    @Test public void calcilateCreditPararams_NotValid(){

        ScoringDataDto request = dto.notCorrectScoringData();

        when(scoringService.calculateCreditParams(request)).thenThrow(ScoringExeption.class);
        assertThrows(ScoringExeption.class, () -> scoringService.calculateCreditParams(request));
    }
}
