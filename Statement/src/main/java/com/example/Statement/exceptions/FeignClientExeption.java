package com.example.Statement.exceptions;

public class FeignClientExeption extends RuntimeException {
    public FeignClientExeption() {
        super();
    }

    public FeignClientExeption(String message) {

        super(message);
    }
}

