package ru.trusov.aston.bank_rest_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trusov.aston.bank_rest_api.exception.ClientNotFoundException;
import ru.trusov.aston.bank_rest_api.exception.LessMoneyInTheAccountException;
import ru.trusov.aston.bank_rest_api.model.BankAccount;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.repository.BankAccountRepository;
import ru.trusov.aston.bank_rest_api.repository.ClientRepository;
import ru.trusov.aston.bank_rest_api.request.TopUpOrWrittenOffBankAccountRequest;
import ru.trusov.aston.bank_rest_api.request.TransferMoneyRequest;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;

    public ClientServiceImpl(ClientRepository clientRepository, BankAccountRepository bankAccountRepository) {
        this.clientRepository = clientRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public Client createClient(Client client) {
        client.setPincode(client.hashCode());
        return clientRepository.save(client);
    }

    @Override
    public Client getClientByNameAndPincode(TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest) {
        topUpOrWrittenOffBankAccountRequest.setPincode(topUpOrWrittenOffBankAccountRequest.hashCode());
        var foundClient = clientRepository.getClientByUsernameAndPincode(topUpOrWrittenOffBankAccountRequest.getUsername(), topUpOrWrittenOffBankAccountRequest.getPincode());
        return foundClient.orElseThrow(ClientNotFoundException::new);
    }

    @Override
    @Transactional
    public void topUpBankAccount(Client client, TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest) {
        client.getBankAccount().setAllMoney(client.getBankAccount().getAllMoney() + topUpOrWrittenOffBankAccountRequest.getMoney());
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void withdrawMoneyBankAccount(Client client, TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest) {
        var currentMoney = client.getBankAccount().getAllMoney();
        var moneyToWriteOff = topUpOrWrittenOffBankAccountRequest.getMoney();
        if (currentMoney < moneyToWriteOff) {
            throw new LessMoneyInTheAccountException();
        } else {
            client.getBankAccount().setAllMoney(currentMoney - moneyToWriteOff);
            clientRepository.save(client);
        }
    }

    @Override
    @Transactional
    public void transferMoneyAccountToAccount(Client fromClient, TransferMoneyRequest transferMoneyRequest, BankAccount bankAccount) {
        var currentMoney = fromClient.getBankAccount().getAllMoney();
        var moneyToWriteOff = transferMoneyRequest.getMoney();
        if (currentMoney < moneyToWriteOff) {
            throw new LessMoneyInTheAccountException();
        } else {
            fromClient.getBankAccount().setAllMoney(currentMoney - moneyToWriteOff);
            clientRepository.save(fromClient);
            bankAccount.setAllMoney(bankAccount.getAllMoney() + moneyToWriteOff);
            bankAccountRepository.save(bankAccount);
        }
    }
}