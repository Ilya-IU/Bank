package com.example.Dto;

import com.example.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage {

    private String address;
    private Theme theme;
    private Long statementId;


}