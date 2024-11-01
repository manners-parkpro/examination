package com.examination.api.presentation.auth;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.LoginUser;
import com.examination.api.model.dto.TokenDto;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {

    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    private final AuthService service;

    @PostMapping("sign-up")
    public ApiResult<AccountDto.ResponseDto> signUp(@RequestBody @Valid AccountDto.RequestDto dto) throws AlreadyEntity, RequiredParamNonException {

        return ApiResult.<AccountDto.ResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.save(dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("login")
    public ApiResult<TokenDto> login(@RequestBody @Valid AccountDto.LoginDto dto) throws RequiredParamNonException {

        return ApiResult.<TokenDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.login(dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("reissue")
    public ApiResult<TokenDto> reissue(@RequestHeader(REFRESH_TOKEN_HEADER) String refreshToken) throws RequiredParamNonException, UserNotFoundException {

        return ApiResult.<TokenDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.reissue(refreshToken))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("logout")
    public ApiResult logout(@RequestBody @Valid AccountDto.LogoutDto dto) throws UserNotFoundException {

        service.logout(dto.getUsername());

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
