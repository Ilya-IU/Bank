package com.example.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanOfferDto  {


    private Long statementId;
    private BigDecimal requestedAmount; //запрошенная сумма клиентом 100_000_0
    private BigDecimal totalAmount;     // сумма с процентами + страховка
    private Integer term;               //срок 10 мес
    private BigDecimal monthlyPayment; //еж мес платеж
    private BigDecimal rate;            // 20% ставка
    private Boolean isInsuranceEnabled; // 10% от суммы кредита -3% от ставки
    private Boolean isSalaryClient;     // -1% от ставки

}



