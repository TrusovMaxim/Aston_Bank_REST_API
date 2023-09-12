package ru.trusov.aston.bank_rest_api.exception;

public class BankAccountNotReplenishedException extends RuntimeException {
    public BankAccountNotReplenishedException(String message) {
        super(message);
    }
}