package com.examination.api.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequiredParamNonException extends Exception {
    public RequiredParamNonException(String message) {
        super(message);
    }
}
