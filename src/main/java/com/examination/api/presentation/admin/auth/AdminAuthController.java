package com.examination.api.presentation.admin.auth;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.front.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/admin/")
public class AdminAuthController {

    private final AuthService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "사용자가 회원가입 할 때 사용하는 API")
    public ApiResult<AccountDto.ResponseDto> signUp(@RequestBody @Valid AccountDto.RequestDto dto) throws AlreadyEntity, RequiredParamNonException {

        return ApiResult.<AccountDto.ResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.save(dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
