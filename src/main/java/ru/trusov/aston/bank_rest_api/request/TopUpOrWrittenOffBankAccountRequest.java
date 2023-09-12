package ru.trusov.aston.bank_rest_api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.trusov.aston.bank_rest_api.annotation.PincodeHave4Digit;

@Data
@EqualsAndHashCode(exclude = {"money"})
public class TopUpOrWrittenOffBankAccountRequest {
    @NotEmpty(message = "Имя не может быть пустым")
    private String username;
    @PincodeHave4Digit(message = "PinCode должен состоять из 4 цифр")
    private Integer pincode;
    @Positive(message = "Поле денег не должно быть 0 или отрицательным")
    private Double money;
}