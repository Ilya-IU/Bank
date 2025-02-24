package com.example.Calculate.services.interfaces;

import com.example.Calculate.dto.CreditDto;
import com.example.Calculate.dto.LoanOfferDto;
import com.example.Calculate.dto.LoanStatementRequestDto;
import com.example.Calculate.dto.ScoringDataDto;

import java.util.List;


public interface CalculateService {

        List<LoanOfferDto> getOffers(LoanStatementRequestDto request);
        CreditDto calculateCreditParams( ScoringDataDto request);

}
