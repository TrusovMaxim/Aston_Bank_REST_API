package ru.trusov.aston.bank_rest_api.service;

import ru.trusov.aston.bank_rest_api.model.BankAccount;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.request.TransferMoneyRequest;

import java.util.List;

public interface BankAccountService {
    void createBankAccountNumber(Client client);

    List<BankAccount> getAllBankAccount();

    BankAccount checkWhetherBankAccountExists(TransferMoneyRequest transferMoneyRequest);
}