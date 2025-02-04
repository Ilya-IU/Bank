package com.example.GateAway.exeptions;

public class FeignClientExeption extends RuntimeException {
    public FeignClientExeption() {
        super();
    }

    public FeignClientExeption(String message) {

        super(message);
    }
}

