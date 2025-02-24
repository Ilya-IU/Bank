package com.example.GateAway.exeptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Decoder start");
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case 400:
                return new NotFoundStatementEntityByid(message.getMessage() != null ? "Bad Request1.1" : "Bad Request1");
            case 404:
                return new NotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
            case 500:
                return new Exception(message.getMessage() != null ? message.getMessage() : "Not foundtEST");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}