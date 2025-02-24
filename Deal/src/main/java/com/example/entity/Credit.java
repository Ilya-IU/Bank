package com.example.entity;


import com.example.Dto.PaymentScheduleElementDto;
import com.example.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit")

public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private BigDecimal amount;

    private int term;

    @Column(name = "monthly_payment")
    private BigDecimal monthPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "payment_schedule")
    private List<PaymentScheduleElementDto> paymentSchedule;

    @Column(name = "insurance_enable")
    private boolean insuranceEnabled;

    @Column(name = "salary_client")
    private boolean salaryClient;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_status")
    private CreditStatus creditStatus;
}
