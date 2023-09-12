package ru.trusov.aston.bank_rest_api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.trusov.aston.bank_rest_api.model.BankAccount;
import ru.trusov.aston.bank_rest_api.response.InfoAboutBankAccountResponse;
import ru.trusov.aston.bank_rest_api.service.BankAccountService;

import java.util.*;

@RestController
@RequestMapping("/aston")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final ModelMapper modelMapper;

    public BankAccountController(BankAccountService bankAccountService, ModelMapper modelMapper) {
        this.bankAccountService = bankAccountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all-bank-accounts")
    public ResponseEntity<List<InfoAboutBankAccountResponse>> getAllBankAccount() {
        var allBankAccount = bankAccountService.getAllBankAccount().stream().map(this::convertBankAccountToInfoAboutBankAccount).toList();
        return ResponseEntity.ok(allBankAccount);
    }

    private InfoAboutBankAccountResponse convertBankAccountToInfoAboutBankAccount(BankAccount bankAccount) {
        var allBankAccount = modelMapper.map(bankAccount, InfoAboutBankAccountResponse.class);
        modelMapper.getTypeMap(BankAccount.class, InfoAboutBankAccountResponse.class)
                .addMappings(modelMapper -> modelMapper.map(name -> name.getClient().getUsername(), InfoAboutBankAccountResponse::setUsername));
        return allBankAccount;
    }
}