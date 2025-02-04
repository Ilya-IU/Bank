package com.example.GateAway.exeptions;

public class NotFoundStatementEntityByid extends RuntimeException {
    public NotFoundStatementEntityByid() {
        super();
    }

    public NotFoundStatementEntityByid(String message) {

        super(message);
    }
}
