package com.example.services;

import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;
import com.example.Dtos;
import com.example.entity.Client;
import com.example.entity.Credit;
import com.example.entity.Statement;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.feignClient.CalculateClient;
import com.example.repository.ClientRepository;
import com.example.repository.CreditRepository;
import com.example.repository.StatementRepository;
import com.example.services.impl.StatementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatementServiceImplTest {

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

    @Test
    public void testGetListOffers() {
        Dtos dto = new Dtos();
        LoanStatementRequestDto requestDto = dto.correctLoanStatementRequest();

        List<LoanOfferDto> loanOfferDto = new ArrayList<>();
        loanOfferDto.add(new LoanOfferDto());

        when(calculateClient.getOffers(requestDto)).thenReturn(loanOfferDto);

        statementService.getListOffers(requestDto);

        verify(clientRepository, times(1)).save(any(Client.class));
        verify(creditRepository, times(1)).save(any(Credit.class));
        verify(statementRepository, times(1)).save(any(Statement.class));
    }

//    @Test
//    public void testUpdateStatusOffer() {
//        Dtos dto = new Dtos();
//        LoanOfferDto loanOfferDto = dto.correctLoanOfferDto();
//
//        doNothing().doThrow(new Exception()).when(statementService).updateStatusOffer(ArgumentMatchers.any());
//        statementService.updateStatusOffer(loanOfferDto);
//        verify(statementRepository, times(1)).findById(anyLong());
//
//
//    }

}