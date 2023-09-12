package ru.trusov.aston.bank_rest_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientErrorResponse {
    private String message;
    private Long timestamp;
}