package com.example.services.impl;

import com.example.Dto.FinishRegistrationRequestDto;

import com.example.Dto.ScoringDataDto;
import com.example.Dto.StatementStatusHistoryDto;
import com.example.entity.Client;
import com.example.entity.Credit;
import com.example.entity.Statement;
import com.example.exeptions.FeignClientExeption;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.feignClient.CalculateClient;
import com.example.repository.StatementRepository;
import com.example.services.interfaces.CalculateScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.enums.ApplicationStatus.CC_APPROVED;
import static com.example.enums.ChangeType.AUTOMATIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateScoringServiceImpl implements CalculateScoringService {
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private CalculateClient calculateClient;

    @Override
    public void scoringCalculateOffer(FinishRegistrationRequestDto requestDto, Long statementId) throws FeignClientExeption{

        log.info("Запуск метода по наполнению scoringDataDto");
        ScoringDataDto scoringDataDto = createScoringDataDto(requestDto, statementId);
        log.info("Метод по наполнению scoringDataDto отработал");
        try {
            log.info("Запуск сервиса Calculate для расчета параметров скоринга scoringDataDto = {}", scoringDataDto);
            calculateClient.calculateCreditParams(scoringDataDto);
        }
        catch (Exception e) {
            log.error("Ошибка FeignClient/Calculate этап скоринга "+e.getMessage());
            throw new FeignClientExeption("Ошибка скоринга " +e.getMessage());
        }

    }


    private ScoringDataDto createScoringDataDto(FinishRegistrationRequestDto requestDto, Long statementId) throws NotFoundStatementEntityByid {
        log.info("Поиск заявки по id, с параметрами , statementid = {}", statementId);
        Statement statement = statementRepository.findById(statementId).orElseThrow(NotFoundStatementEntityByid::new);
        log.info("Заявка найдена statement = {}", statement);
        Client client = statement.getClient();
        log.info("Клинет найден statement = {}", client);
        Credit credit = statement.getCredit();
        log.info("Кредит найден statement = {}", credit);
        log.info("Наполнение scoringDataDto");

        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(credit.getAmount())
                .term(credit.getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .birthdate(client.getBirthdate())
                .passportNumber(client.getPassport().getSeries())
                .passportSeries(client.getPassport().getSeries())
                .gender(requestDto.getGender())
                .employment(requestDto.getEmployment())
                .maritalStatus(requestDto.getMaritalStatus())
                .dependentAmount(requestDto.getDependentAmount())
                .passportIssueBranch(requestDto.getPassportIssueBranch())
                .passportIssueDate(requestDto.getPassportIssueDate())
                .accountNumber(requestDto.getAccountNumber())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .build();
        log.info("scoringDataDto заполнено параметрами ={}", scoringDataDto);
        statement.setStatus(CC_APPROVED);
        List<StatementStatusHistoryDto> historyStatuses = statement.getStatementStatusHistoryDto();
        historyStatuses.add(new StatementStatusHistoryDto( CC_APPROVED, LocalDate.now(), AUTOMATIC));
        log.info("История статусов заявки в бд historyStatuses = {}", historyStatuses);
        statement.setStatementStatusHistoryDto(historyStatuses);
        log.info("Обновление статуса заявки в БД");

        statementRepository.save(statement);
        log.info("Сохранение заявки в бд/возвращение scoringDataDto");
        return scoringDataDto;
    }
}
