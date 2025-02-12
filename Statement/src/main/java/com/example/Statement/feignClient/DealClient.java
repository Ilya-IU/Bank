package com.example.Statement.feignClient;

import com.example.Statement.Dto.LoanOfferDto;
import com.example.Statement.Dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "deal", url = "${feign.deal.url}")
public interface DealClient {

    @PostMapping(value = "/deal/statement")
    List<LoanOfferDto> getOffers(@Valid @RequestBody LoanStatementRequestDto requestDto) ;

    @PostMapping(value = "/deal/offer/select")
    void updateStatusHistory(@RequestBody LoanOfferDto requestDto);


}