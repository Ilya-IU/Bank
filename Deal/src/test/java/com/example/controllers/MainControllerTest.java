package com.example.controllers;


import com.example.Dto.EmailMessage;
import com.example.Dto.FinishRegistrationRequestDto;
import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;
import com.example.Dtos;
import com.example.exeptions.FeignClientExeption;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.services.AdminService;
import com.example.services.KafkaService;
import com.example.services.impl.CalculateScoringServiceImpl;
import com.example.services.interfaces.StatementService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Dtos dto;

    @Mock
    private StatementService statementService;

    @Mock
    private AdminService adminService;

    @Mock
    private KafkaService kafkaService;

    @Mock
    private CalculateScoringServiceImpl calculateScoringService;

    @InjectMocks
    private MainController mainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        dto = new Dtos();
    }

    @Test
    public void getOffers_ReturnValid() throws Exception {

        LoanStatementRequestDto request = dto.correctLoanStatementRequest();

        when(statementService.getListOffers((ArgumentMatchers.any())))
                .thenReturn(new ArrayList<LoanOfferDto>());

        mockMvc.perform(
                        post("/deal/statement")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }

    @Test
    public void getOffers_Return_NotValid() throws Exception {

        when(statementService.getListOffers((ArgumentMatchers.any())))
                .thenThrow(new FeignClientExeption());

        mockMvc.perform(
                        post("/deal/statement")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    public void selectStatusHistory_Return_Valid() throws Exception {
        LoanOfferDto requestOffer = dto.correctLoanOfferDto();

        doNothing().when(statementService).updateStatusOffer((ArgumentMatchers.any()));
        mockMvc.perform(
                        post("/deal/offer/select")
                                .content(objectMapper.writeValueAsString(requestOffer))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());


    }

    @Test
    public void selectStatusHistory_Return_NotValid_FeignException() throws Exception {
        doThrow(new FeignClientExeption()).when(statementService).updateStatusOffer((ArgumentMatchers.any()));

        mockMvc.perform(
                        post("/deal/offer/select")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());


    }

    @Test
    public void selectStatusHistory_Return_NotValid_EntityException() throws Exception {

        doThrow(new NotFoundStatementEntityByid()).when(kafkaService)
                .finishRegistration((ArgumentMatchers.any()));

        mockMvc.perform(
                        post("/deal/offer/select")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());


    }

    @Test
    public void FinishRegistrationAndCredit_Return_Valid() throws Exception {
        FinishRegistrationRequestDto request = dto.correctFinishRegistrationRequestDto();
        Random r = new Random();
        Long id = r.nextLong();
        doNothing().when(calculateScoringService).scoringCalculateOffer(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/calculate/" + id)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void FinishRegistrationAndCredit_Return_notValid_FeignException() throws Exception {

        Random r = new Random();
        Long id = r.nextLong();
        doThrow(new FeignClientExeption()).when(calculateScoringService).scoringCalculateOffer(ArgumentMatchers.any(), ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/calculate/" + id)
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void FinishRegistrationAndCredit_Return_notValid_EntityException() throws Exception {

        Random r = new Random();
        Long id = r.nextLong();
        doThrow(new NotFoundStatementEntityByid()).when(kafkaService).createDocuments(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/calculate/" + id)
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendDocuments_Valid() throws Exception {
        EmailMessage request = dto.correctEmailMessage();
        Random r = new Random();
        Long id = r.nextLong();
        doNothing().when(kafkaService).sendDocuments(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/send")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    public void sendDocuments_NotValid() throws Exception {

        Random r = new Random();
        Long id = r.nextLong();
        doThrow(new NotFoundStatementEntityByid()).when(kafkaService).sendDocuments(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/send")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendSes_Valid() throws Exception {
        EmailMessage request = dto.correctEmailMessage();
        Random r = new Random();
        Long id = r.nextLong();
        doNothing().when(kafkaService).sendDocuments(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/sign")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    public void sendSes_NotValid() throws Exception {
        Random r = new Random();
        Long id = r.nextLong();
        doThrow(new NotFoundStatementEntityByid()).when(kafkaService).sendSes(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/send")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
    @Test
    public void sendCreditIssued_Valid() throws Exception {
        EmailMessage request = dto.correctEmailMessage();
        Random r = new Random();
        Long id = r.nextLong();
        doNothing().when(kafkaService).sendCreditIssued(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/code")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @Test
    public void sendCreditIssued_NotValid() throws Exception {

        Random r = new Random();
        Long id = r.nextLong();
        doThrow(new NotFoundStatementEntityByid()).when(kafkaService).sendCreditIssued(ArgumentMatchers.any());

        mockMvc.perform(
                        post("/deal/document/"+id+"/code")
                                .content(objectMapper.writeValueAsString(""))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
//    @Test
//    public void getStatement_NotValid() throws Exception {
//
//        Random r = new Random();
//        String id = r.nextLong()+"";
//
//        when(adminService.getStatementById(id)).thenThrow(new NotFoundStatementEntityByid());
//
//        mockMvc.perform(
////                get("/deal/admin/statement/"+id)
//                        get("/deal/admin/statement/"+id)
//                                .content(objectMapper.writeValueAsString(id))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    public void getAllStatement_NotValid() throws Exception {
//
//
//
//        when(adminService.getAllStatements()).thenThrow(new SQLException());
//
//        mockMvc.perform(
//                        post("/deal/admin/statement/")
////                                .content(objectMapper.writeValueAsString())
////                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isBadRequest());
//    }
}


