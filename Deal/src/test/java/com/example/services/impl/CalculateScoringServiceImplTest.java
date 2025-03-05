package com.example.services.impl;

import com.example.Dto.FinishRegistrationRequestDto;
import com.example.Dto.LoanOfferDto;
import com.example.Dto.ScoringDataDto;
import com.example.Dtos;
import com.example.entity.Client;
import com.example.entity.Credit;
import com.example.entity.Statement;
import com.example.enums.ApplicationStatus;
import com.example.enums.ChangeType;
import com.example.exeptions.FeignClientExeption;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.feignClient.CalculateClient;
import com.example.repository.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CalculateScoringServiceImplTest {

    @Mock
    private StatementRepository statementRepository;

    @Mock
    private CalculateClient calculateClient;

    @InjectMocks
    private CalculateScoringServiceImpl calculateScoringService;

    private FinishRegistrationRequestDto requestDto;
    private Statement statement;
    private Client client;
    private Credit credit;
    private LoanOfferDto appliedOffer;
    private Dtos dtos;

    @BeforeEach
    void setUp() {
        dtos = new Dtos();
        requestDto = dtos.correctFinishRegistrationRequestDto();
        client = dtos.createClientEntity();
        credit = dtos.createCreditEntity();
        appliedOffer = dtos.correctLoanOfferDto();
        statement = dtos.createStatementEntity();
        statement.setClient(client);
        statement.setCredit(credit);
        statement.setAppliedOffer(appliedOffer);
    }

//    @Test
//    void scoringCalculateOffer_valid() throws NotFoundStatementEntityByid, FeignClientExeption {
//        Long statementId = 1L;
//        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
//
//        calculateScoringService.scoringCalculateOffer(requestDto, statementId);
//
//        verify(calculateClient, times(1)).calculateCreditParams(any(ScoringDataDto.class));
//
//        ArgumentCaptor<Statement> statementCaptor = ArgumentCaptor.forClass(Statement.class);
//        verify(statementRepository, times(1)).save(statementCaptor.capture());
//        Statement savedStatement = statementCaptor.getValue();
//
//        assertEquals(ApplicationStatus.CC_APPROVED, savedStatement.getStatus());
//        assertNotNull(savedStatement.getStatementStatusHistoryDto());
//        assertEquals(1, savedStatement.getStatementStatusHistoryDto().size());
//        assertEquals(ApplicationStatus.CC_APPROVED, savedStatement.getStatementStatusHistoryDto().get(0).getStatus());
//
//    }

    @Test
    void scoringCalculateOffer_NotValid() {
        Long statementId = 1L;
        when(statementRepository.findById(statementId)).thenReturn(Optional.empty());

        assertThrows(NotFoundStatementEntityByid.class, () -> calculateScoringService.scoringCalculateOffer(requestDto, statementId));

        verify(calculateClient, never()).calculateCreditParams(any(ScoringDataDto.class));
        verify(statementRepository, never()).save(any(Statement.class));
    }

//    @Test
//    void scoringCalculateOffer_NotValid() throws NotFoundStatementEntityByid {
//        Long statementId = 1L;
//        when(statementRepository.findById(statementId)).thenReturn(Optional.of(statement));
//        doThrow(new RuntimeException("Calculate service failed")).when(calculateClient).calculateCreditParams(any(ScoringDataDto.class));
//
//        assertThrows(FeignClientExeption.class, () -> calculateScoringService.scoringCalculateOffer(requestDto, statementId));
//
//        verify(calculateClient, times(1)).calculateCreditParams(any(ScoringDataDto.class));
//        verify(statementRepository, never()).save(any(Statement.class));
//    }

}