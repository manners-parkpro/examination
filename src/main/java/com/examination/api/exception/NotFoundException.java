package com.examination.api.exception;

public class NotFoundException extends Exception {
    public static final int USER_NOT_FOUND = 100;
    public static final int REQUIRED_PARAM_NOT_FOUND = 200;

    private int code;

    public NotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public NotFoundException(int code) {
        super("Not Found Exception: " + code);
        this.code = code;
    }

    public NotFoundException(String product_not_found) {
        super(product_not_found);
    }
}
