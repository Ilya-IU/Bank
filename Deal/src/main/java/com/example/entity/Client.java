package com.example.entity;

import com.example.Dto.EmploymentDto;
import com.example.Dto.Passport;
import com.example.enums.Gender;
import com.example.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "last_Name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;

    private LocalDate birthdate;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private int dependentAmount;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Passport passport;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private EmploymentDto employment;
    @Column(name = "account_number")
    private String accountNumber;
}
