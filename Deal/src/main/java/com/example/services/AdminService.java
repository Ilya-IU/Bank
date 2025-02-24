package com.example.services;

import com.example.entity.Statement;
import com.example.exeptions.NotFoundStatementEntityByid;
import com.example.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final StatementRepository statementRepository;


    public Statement getStatementById(String Id) throws NotFoundStatementEntityByid {
        log.info("Админ запрос поиск заявки по id = {}", Id);
        return statementRepository.findById(Long.parseLong(Id));

    }

    public List<Statement> getAllStatements() throws SQLException {
        log.info("Получение всех заявок из БД");
        return statementRepository.findAll();
    }
}
