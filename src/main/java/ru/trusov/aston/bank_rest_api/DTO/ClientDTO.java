package ru.trusov.aston.bank_rest_api.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.trusov.aston.bank_rest_api.annotation.PincodeHave4Digit;

@Data
public class ClientDTO {
    @NotEmpty(message = "Имя не может быть пустым")
    private String username;
    @PincodeHave4Digit(message = "PinCode должен состоять из 4 цифр")
    private Integer pincode;
}