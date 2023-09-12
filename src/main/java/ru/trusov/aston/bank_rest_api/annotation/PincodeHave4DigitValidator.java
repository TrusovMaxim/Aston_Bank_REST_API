package ru.trusov.aston.bank_rest_api.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PincodeHave4DigitValidator implements ConstraintValidator<PincodeHave4Digit, Integer> {
    @Override
    public boolean isValid(Integer pincode, ConstraintValidatorContext constraintValidatorContext) {
        return pincode.toString().length() == 4;
    }
}