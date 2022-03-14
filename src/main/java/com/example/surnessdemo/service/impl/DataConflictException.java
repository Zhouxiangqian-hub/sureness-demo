package com.example.surnessdemo.service.impl;



public class DataConflictException extends RuntimeException {

    public DataConflictException(String msg) {
        super(msg);
    }
}
