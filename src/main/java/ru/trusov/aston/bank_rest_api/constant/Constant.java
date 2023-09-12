package ru.trusov.aston.bank_rest_api.constant;

public interface Constant {
    String CLIENT_NOT_FOUND_EXCEPTION_TEXT = "Данный клиент не найден. Вы ввели неверное имя или пинкод";
    String LESS_MONEY_IN_THE_ACCOUNT_EXCEPTION_TEXT = "Денег на счету меньше, чем требуется списать";
    String BANK_ACCOUNT_NOT_FOUND_EXCEPTION_TEXT = "Данный банковский счет не найден. Уточните, пожалуйста, и попробуйте снова";
}