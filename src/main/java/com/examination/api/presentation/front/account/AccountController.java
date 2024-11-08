package com.examination.api.presentation.front.account;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.LoginUser;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.service.front.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/")
@Tag(name = "User", description = "User 정보 조회 및 비밀번호 변경 API")
public class AccountController {

    private final AccountService service;

    @GetMapping("detail")
    @Operation(summary = "사용자 정보", description = "사용자 상세정보 API")
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
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API")
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
