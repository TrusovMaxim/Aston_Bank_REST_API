package ru.trusov.aston.bank_rest_api.service;

import ru.trusov.aston.bank_rest_api.model.BankAccount;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.request.TopUpOrWrittenOffBankAccountRequest;
import ru.trusov.aston.bank_rest_api.request.TransferMoneyRequest;

public interface ClientService {
    Client createClient(Client client);

    Client getClientByNameAndPincode(TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest);

    void topUpBankAccount(Client client, TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest);

    void withdrawMoneyBankAccount(Client client, TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest);

    void transferMoneyAccountToAccount(Client fromClient, TransferMoneyRequest transferMoneyRequest, BankAccount bankAccount);
}