package com.example.feignClient;

import com.example.Dto.CreditDto;
import com.example.Dto.LoanOfferDto;
import com.example.Dto.LoanStatementRequestDto;
import com.example.Dto.ScoringDataDto;
import com.example.exeptions.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "calculate", url = "${feign.calculate.url}", configuration = FeignClientConfiguration.class)
public interface CalculateClient {

    @PostMapping(value = "/calculate/offer")
    List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto requestDto) ;

    @PostMapping(value = "/calculate/calc")
    CreditDto calculateCreditParams(@Valid @RequestBody ScoringDataDto requestDto) ;
}

