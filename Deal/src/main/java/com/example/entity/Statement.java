package com.example.entity;



import com.example.Dto.LoanOfferDto;
import com.example.enums.ApplicationStatus;
import com.example.Dto.StatementStatusHistoryDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "statement")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id",referencedColumnName = "id")
    private Credit credit;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @CreatedDate
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(name ="applied_offer" ,columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;
    @Column(name = "ses_code")
    private String sesCode;

    @Type(type = "jsonb")
    @Column(name="status_history",columnDefinition = "jsonb")
    private List<StatementStatusHistoryDto> statementStatusHistoryDto;

}
