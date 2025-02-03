package com.example.Dto;




import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanStatementRequestDto {

    @NotNull(message = "Укажите сумму кредита")
    @Min(value = 30000, message = "Сумма кредита не может быть менее 30000 рублей")
    @Schema(description = "Запрашиваемая сумма кредита", example = "30000")
    private BigDecimal amount;

    @NotNull(message = "Укажите срок кредита")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    @Schema(description = "Срок кредита", example = "6")
    private Integer term;

    @NotBlank(message = "Укажите ваше имя")
    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "Имя указано некорректно")
    @Schema(description = "Имя клиента", example = "Василий")
    private String firstName;

    @NotBlank(message = "Укажите вашу фамилию")
    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "Фамилия указана некорректно")
    @Schema(description = "Фамилия клиента", example = "Васильев")
    private String lastName;

    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "Отчество указано некорректно")
    @Schema(description = "Отчество клиента", example = "Васильевич")
    private String middleName;

    @NotBlank(message = "Укажите вашу почту")
    @Email(regexp = "^(.+)@(.+)$", message = "Введен некорретный адрес почты")
    @Schema(description = "Электронная почта клиента", example = "VVvasilev@gmail.ru")
    private String email;

    @NotNull(message = "Укажите дату рождения")
    @Past(message = "The birthday must be in the past")

    @Schema(description = "Дата рождения клиента", example = "1990-01-01")
    private LocalDate birthdate;

    @NotBlank(message = "Укажите серию паспорта")
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта паспорта состоит 4 цифр")
    @Schema(description = "Серия паспорта клиента", example = "1234")
    private String passportSeries;

    @NotBlank(message = "Укажите номер паспорта")
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта состоит из 6 цифр")
    @Schema(description = "Номер паспорта клиента", example = "123456")
    private String passportNumber;
}
