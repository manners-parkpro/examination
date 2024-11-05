package com.examination.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {
    private String message;

    @Override
    public void initialize(EmailValid constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) { return false; }
        try {
            return isEmailRegex(value);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isEmailRegex(String str) {
        if (str.length() > 100) return false;
        String pattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{1,4}\\b";
        return Pattern.matches(pattern, str);
    }
}
