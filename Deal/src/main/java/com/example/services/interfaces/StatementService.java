package com.example.services.interfaces;

import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> getListOffers(LoanStatementRequestDto requestDto);

    void updateStatusOffer(LoanOfferDto requestDto);
}
