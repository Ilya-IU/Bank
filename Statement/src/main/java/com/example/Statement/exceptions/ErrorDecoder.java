package com.example.Statement.exceptions;

import feign.Response;

public class ErrorDecoder implements feign.codec.ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 400:
                return new FeignClientExeption("Ошибка 400 от фейн клиента");
            case 404:
                return new FeignClientExeption("Ошибка 404 от фейн клиента");
            case 500:
                return new FeignClientExeption("Ошибка FeignClient'a_500");
            default:
                return new Exception("Generic error");
        }
    }
}