package com.examination.api.validation;

import com.examination.api.util.CommonUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
            return CommonUtil.isEmailRegex(value);
        } catch (Exception e) {
            return false;
        }
    }
}
