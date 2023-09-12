package ru.trusov.aston.bank_rest_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.trusov.aston.bank_rest_api.exception.BankAccountNumberNotFoundException;
import ru.trusov.aston.bank_rest_api.model.BankAccount;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.repository.BankAccountRepository;
import ru.trusov.aston.bank_rest_api.request.TransferMoneyRequest;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public void createBankAccountNumber(Client client) {
        bankAccountRepository.save(new BankAccount(client.getId(), 0.0, client));
    }

    @Override
    public List<BankAccount> getAllBankAccount() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount checkWhetherBankAccountExists(TransferMoneyRequest transferMoneyRequest) {
        var foundBankAccount = bankAccountRepository.checkWhetherBankAccountExists(transferMoneyRequest.getBankAccountNumber());
        return foundBankAccount.orElseThrow(BankAccountNumberNotFoundException::new);
    }
}