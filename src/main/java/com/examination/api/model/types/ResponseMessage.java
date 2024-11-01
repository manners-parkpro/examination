package com.examination.api.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS("정상 처리 되었습니다."),
    ALREADY_EXIST("이미 있는 값 입니다."),
    NOT_FOUND("존재하지 않는 값 입니다."),
    REQUIRED("필수 데이터가 존재하지 않습니다."),
    UNAUTHORIZED("사용자 인증에 실패 하였습니다."),
    WRONG_PARAMETER("잘못된 파라메터 입니다.");

    private final String message;


}
