package ru.trusov.aston.bank_rest_api.exception;

public class MoneyNotWrittenOffException extends RuntimeException {
    public MoneyNotWrittenOffException(String message) {
        super(message);
    }
}