package ru.trusov.aston.bank_rest_api.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InfoAboutBankAccountResponse {
    private Long bankAccountNumber;
    private String username;
    private Double allMoney;
}