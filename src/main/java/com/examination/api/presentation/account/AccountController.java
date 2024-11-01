package com.examination.api.presentation.account;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.LoginUser;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
public class AccountController {

    private final AccountService service;

    @GetMapping("detail")
    public ApiResult<AccountDto.ResponseDto> detail(@AuthenticationPrincipal LoginUser loginUser) throws RequiredParamNonException, UserNotFoundException {
        if (loginUser == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        return ApiResult.<AccountDto.ResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.findByUsername(loginUser.getPrincipal()))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("modify/password")
    public ApiResult modifyPassword(@AuthenticationPrincipal LoginUser loginUser, @RequestBody String password) throws RequiredParamNonException, UserNotFoundException, AlreadyEntity {
        if (loginUser == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        service.modifyPassword(loginUser.getPrincipal(), password);

        return ApiResult.<AccountDto.ResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
