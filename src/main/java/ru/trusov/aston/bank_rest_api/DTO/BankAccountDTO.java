package ru.trusov.aston.bank_rest_api.DTO;

import lombok.Data;

@Data
public class BankAccountDTO {
    private Long bankAccountNumber;
    private Double allMoney;
    private ClientDTO client;
}