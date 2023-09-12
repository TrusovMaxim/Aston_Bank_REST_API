package ru.trusov.aston.bank_rest_api.exception;

public class ClientNotCreatedException extends RuntimeException {
    public ClientNotCreatedException(String message) {
        super(message);
    }
}