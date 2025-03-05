package com.example.services.impl;

import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;
import com.example.Dtos;
import com.example.entity.Client;
import com.example.entity.Credit;
import com.example.entity.Statement;
import com.example.enums.ApplicationStatus;
import com.example.exeptions.FeignClientExeption;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.feignClient.CalculateClient;
import com.example.repository.ClientRepository;
import com.example.repository.CreditRepository;
import com.example.repository.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

    @Mock
    private CalculateClient calculateClient;

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private StatementServiceImpl statementService;

    private LoanStatementRequestDto requestDto;
    private List<LoanOfferDto> loanOfferDto;
    private Client client;
    private Credit credit;
    private Statement statement;
    private LoanOfferDto offerDto;
    private Dtos dtos;



    @BeforeEach
    void setUp() {
        dtos = new Dtos();
        requestDto = dtos.correctLoanStatementRequest();
        loanOfferDto = dtos.correctListLoanOfferDto();
        client = dtos.createClientEntity();
        credit = dtos.createCreditEntity();
        statement = dtos.createStatementEntity();
        offerDto = dtos.correctLoanOfferDto();
        statement.setClient(client);
        statement.setCredit(credit);

        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);
        lenient().when(creditRepository.save(any(Credit.class))).thenReturn(credit);
        lenient().when(statementRepository.save(any(Statement.class))).thenReturn(statement);
    }

    @Test
    void getListOffers_valid() {
        when(calculateClient.getOffers(requestDto)).thenReturn(loanOfferDto);

        List<LoanOfferDto> result = statementService.getListOffers(requestDto);

        assertEquals(loanOfferDto, result);
        verify(calculateClient, times(1)).getOffers(requestDto);
        verify(clientRepository, times(1)).save(any(Client.class));
        verify(creditRepository, times(1)).save(any(Credit.class));
        verify(statementRepository, times(1)).save(any(Statement.class));
    }

    @Test
    void getListOffers_NotValid() {
        when(calculateClient.getOffers(requestDto)).thenThrow(new RuntimeException("Feign client error"));

        assertThrows(FeignClientExeption.class, () -> statementService.getListOffers(requestDto));
        verify(calculateClient, times(1)).getOffers(requestDto);
        verify(clientRepository, never()).save(any(Client.class));
        verify(creditRepository, never()).save(any(Credit.class));
        verify(statementRepository, never()).save(any(Statement.class));
    }

    @Test
    void updateStatusOffer_Valid() throws NotFoundStatementEntityByid {
        when(statementRepository.findById(offerDto.getStatementId())).thenReturn(Optional.of(statement));

        statementService.updateStatusOffer(offerDto);

        assertEquals(ApplicationStatus.PREAPPROVAL, statement.getStatus());
        assertNotNull(statement.getStatementStatusHistoryDto());
        assertEquals(1, statement.getStatementStatusHistoryDto().size());
        assertEquals(ApplicationStatus.PREAPPROVAL, statement.getStatementStatusHistoryDto().get(0).getStatus());
        assertEquals(offerDto, statement.getAppliedOffer());
        verify(statementRepository, times(1)).findById(offerDto.getStatementId());
        verify(statementRepository, times(1)).save(statement);
    }

    @Test
    void updateStatusOffer_notValid() {
        when(statementRepository.findById(offerDto.getStatementId())).thenReturn(Optional.empty());

        assertThrows(NotFoundStatementEntityByid.class, () -> statementService.updateStatusOffer(offerDto));

        verify(statementRepository, times(1)).findById(offerDto.getStatementId());
        verify(statementRepository, never()).save(any(Statement.class));
    }
}