package com.examination.api.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResultCode {

    ERROR("ERROR"),
    SUCCESS("SUCCESS");

    private final String code;
}
