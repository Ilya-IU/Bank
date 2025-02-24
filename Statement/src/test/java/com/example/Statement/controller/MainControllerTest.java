package com.example.Statement.controller;


import com.example.Statement.Dto.LoanOfferDto;
import com.example.Statement.Dto.LoanStatementRequestDto;
import com.example.Statement.Dtos;
import com.example.Statement.StatementApplication;
import com.example.Statement.controllers.MainController;
import com.example.Statement.exceptions.FeignClientExeption;
import com.example.Statement.feignClient.DealClient;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StatementApplication.class)
@TestPropertySource("classpath:.env")
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    DealClient dealClient;

    @InjectMocks
    MainController mainController;

    Dtos dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        dto = new Dtos();
    }

    @Test
    public void getOffers_Valid() throws Exception {
        LoanStatementRequestDto request = dto.correctLoanStatementRequest();

        when(dealClient.getOffers((ArgumentMatchers.any())))
                .thenReturn(new ArrayList<LoanOfferDto>());
        mockMvc.perform(
                        post("/statement")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void getOffers_NotValid() throws Exception {


        when(dealClient.getOffers((ArgumentMatchers.any())))
                .thenThrow(FeignClientExeption.class);
        mockMvc.perform(
                        post("/statement")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateStatusHistory_Valid() throws Exception {
        LoanOfferDto request = dto.correctLoanOfferDto();
        doNothing().when(dealClient).updateStatusHistory(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/statement/offer")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateStatusHistory_NotValid() throws Exception {

        doThrow(new FeignClientExeption()).when(dealClient).updateStatusHistory(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/statement/offer")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }


}
