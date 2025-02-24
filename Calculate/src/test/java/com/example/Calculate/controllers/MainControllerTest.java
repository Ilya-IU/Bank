package com.example.Calculate.controllers;

import com.example.Calculate.Dto;
import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.LoanOfferDto;
import com.example.Calculate.dto.LoanStatementRequestDto;
import com.example.Calculate.dto.ScoringDataDto;
import com.example.Calculate.exeptions.PreScorExeption;
import com.example.Calculate.exeptions.ScoringExeption;
import com.example.Calculate.services.interfaces.CalculateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    Dto dto;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CalculateService calculateService;


    @InjectMocks
    private MainController mainController;



    @BeforeEach void setUp() {
        MockitoAnnotations.initMocks(this);
        dto = new Dto();
    }

    @Test
    public void getOffers_ReturnValid() throws Exception {
        LoanStatementRequestDto request = dto.correctLoanStatementRequest();

        when(mainController.getOffers((ArgumentMatchers.any())))
                .thenReturn(new ArrayList<LoanOfferDto>());

        mockMvc.perform(
                        post("/calculate/offer")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
    @Test
    public void getOffers_ReturnNotValid() throws Exception {

        LoanStatementRequestDto request = dto.notCorrectLoanStatementRequest();

        when(mainController.getOffers((ArgumentMatchers.any())))
                .thenThrow(new PreScorExeption());
        mockMvc.perform(
                        post("/calculate/offer")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    public void calculateCreditParams_Valid() throws Exception {

        ScoringDataDto request = dto.correctScoringData();

        when(mainController.calculateCreditParams(request)).thenReturn(new CreditDto());
        mockMvc.perform(
                        post("/calculate/calc")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void calculateCreditParams_NotValid() throws Exception {

        ScoringDataDto request = dto.notCorrectScoringData();

        when(mainController.calculateCreditParams(request)).thenThrow(ScoringExeption.class);
        mockMvc.perform(
                        post("/calculate/calc")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
}

