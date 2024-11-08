package com.examination.api.core;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {

        log.error("handleRuntimeException" + e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.builder()
                        .code(ApiResult.RESULT_CODE_ERROR)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e) {

        log.error("handleNotFoundException" + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.builder()
                        .code(ApiResult.RESULT_CODE_NOT_FOUND)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyEntity.class)
    public ResponseEntity handleAlreadyEntity(AlreadyEntity e) {

        log.error("handleAlreadyEntity" + e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResult.builder()
                        .code(ApiResult.RESULT_CODE_ERROR)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {

        log.error("handleUserNotFoundException" + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResult.builder()
                        .code(ApiResult.RESULT_CODE_NOT_FOUND)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException :: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.builder()
                        .code(ApiResult.RESULT_CODE_BAD_REQUEST)
                        .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build());
    }
}
