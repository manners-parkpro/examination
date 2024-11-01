package com.examination.api.model.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {

    final public static int RESULT_CODE_OK = 200;
    final public static int RESULT_CODE_BAD_REQUEST = 400;
    final public static int RESULT_CODE_BAD_UNAUTHORIZED = 401;
    final public static int RESULT_CODE_NOT_FOUND = 404;
    final public static int RESULT_CODE_ERROR = 500;
    final public static int RESULT_CODE_INVALID = 501;

    private int code;
    private T data;
    private String message;

    public ApiResult(int code) {
        this.code = code;
    }
}
