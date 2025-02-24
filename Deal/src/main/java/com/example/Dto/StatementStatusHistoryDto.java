package com.example.Dto;


import com.example.enums.ApplicationStatus;
import com.example.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StatementStatusHistoryDto {
    private ApplicationStatus status;
    private LocalDate date;
    private ChangeType changeType;
}
