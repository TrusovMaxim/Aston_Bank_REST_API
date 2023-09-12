package ru.trusov.aston.bank_rest_api.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.trusov.aston.bank_rest_api.DTO.ClientDTO;
import ru.trusov.aston.bank_rest_api.constant.Constant;
import ru.trusov.aston.bank_rest_api.exception.*;
import ru.trusov.aston.bank_rest_api.model.Client;
import ru.trusov.aston.bank_rest_api.request.TopUpOrWrittenOffBankAccountRequest;
import ru.trusov.aston.bank_rest_api.request.TransferMoneyRequest;
import ru.trusov.aston.bank_rest_api.service.BankAccountService;
import ru.trusov.aston.bank_rest_api.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/aston")
public class ClientController {
    private final ClientService clientService;
    private final BankAccountService bankAccountService;
    private final ModelMapper modelMapper;

    public ClientController(ClientService clientService, BankAccountService bankAccountService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createClient(@RequestBody @Valid ClientDTO clientDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorMessage = messageHandler(bindingResult);
            throw new ClientNotCreatedException(errorMessage.toString());
        } else {
            var client = clientService.createClient(convertClientDTOToClient(clientDTO));
            bankAccountService.createBankAccountNumber(client);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PostMapping("/top-up-bank-account")
    public ResponseEntity<HttpStatus> topUpMyBankAccount(@RequestBody @Valid TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorMessage = messageHandler(bindingResult);
            throw new BankAccountNotReplenishedException(errorMessage.toString());
        } else {
            var client = clientService.getClientByNameAndPincode(topUpOrWrittenOffBankAccountRequest);
            clientService.topUpBankAccount(client, topUpOrWrittenOffBankAccountRequest);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PostMapping("/withdraw-money-bank-account")
    public ResponseEntity<HttpStatus> withdrawMoneyBankAccount(@RequestBody @Valid TopUpOrWrittenOffBankAccountRequest topUpOrWrittenOffBankAccountRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorMessage = messageHandler(bindingResult);
            throw new MoneyNotWrittenOffException(errorMessage.toString());
        } else {
            var client = clientService.getClientByNameAndPincode(topUpOrWrittenOffBankAccountRequest);
            clientService.withdrawMoneyBankAccount(client, topUpOrWrittenOffBankAccountRequest);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    @PostMapping("transfer-money-from-account-to-account")
    public ResponseEntity<HttpStatus> transferMoneyFromAccountToAccount(@RequestBody @Valid TransferMoneyRequest transferMoneyRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorMessage = messageHandler(bindingResult);
            throw new MoneyNotWrittenOffException(errorMessage.toString());
        } else {
            var foundClientFromWhomTransfer = clientService.getClientByNameAndPincode(convertTransferMoneyRequestToTopUpOrWrittenOffBankAccountRequest(transferMoneyRequest));
            var foundBankAccountForWhomTransfer = bankAccountService.checkWhetherBankAccountExists(transferMoneyRequest);
            clientService.transferMoneyAccountToAccount(foundClientFromWhomTransfer, transferMoneyRequest, foundBankAccountForWhomTransfer);
            return ResponseEntity.ok(HttpStatus.OK);
        }
    }

    private StringBuilder messageHandler(BindingResult bindingResult) {
        var errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append("; ");
        }
        return errorMessage;
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(ClientNotFoundException exc) {
        var clientErrorException = new ClientErrorResponse(Constant.CLIENT_NOT_FOUND_EXCEPTION_TEXT, System.currentTimeMillis());
        return new ResponseEntity<>(clientErrorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(BankAccountNumberNotFoundException exc) {
        var clientErrorException = new ClientErrorResponse(Constant.BANK_ACCOUNT_NOT_FOUND_EXCEPTION_TEXT, System.currentTimeMillis());
        return new ResponseEntity<>(clientErrorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(LessMoneyInTheAccountException exc) {
        var clientErrorException = new ClientErrorResponse(Constant.LESS_MONEY_IN_THE_ACCOUNT_EXCEPTION_TEXT, System.currentTimeMillis());
        return new ResponseEntity<>(clientErrorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(ClientNotCreatedException exc) {
        return auxiliaryHandleException(exc);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(BankAccountNotReplenishedException exc) {
        return auxiliaryHandleException(exc);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(MoneyNotWrittenOffException exc) {
        return auxiliaryHandleException(exc);
    }

    private ResponseEntity<ClientErrorResponse> auxiliaryHandleException(Exception exc) {
        var clientErrorResponse = new ClientErrorResponse(exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Client convertClientDTOToClient(ClientDTO clientDTO) {
        return modelMapper.map(clientDTO, Client.class);
    }

    private TopUpOrWrittenOffBankAccountRequest convertTransferMoneyRequestToTopUpOrWrittenOffBankAccountRequest(TransferMoneyRequest transferMoneyRequest) {
        return modelMapper.map(transferMoneyRequest, TopUpOrWrittenOffBankAccountRequest.class);
    }
}