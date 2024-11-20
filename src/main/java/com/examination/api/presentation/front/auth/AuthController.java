package com.examination.api.presentation.front.auth;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.TokenDto;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.front.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
@Tag(name = "User", description = "User 회원가입, 로그인, 토큰 재발행, 로그아웃 API")
public class AuthController {

    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
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

    @PostMapping("login")
    @Operation(summary = "로그인", description = "사용자 로그인 API")
    public ApiResult<TokenDto> login(@RequestBody @Valid AccountDto.LoginDto dto) throws RequiredParamNonException {

        return ApiResult.<TokenDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.login(dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("reissue")
    @Operation(summary = "토큰 재발행", description = "사용자 토큰 만료시 토큰 재발행 API")
    public ApiResult<TokenDto> reissue(@RequestHeader(REFRESH_TOKEN_HEADER) String refreshToken) throws RequiredParamNonException, UserNotFoundException {

        return ApiResult.<TokenDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.reissue(refreshToken))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("logout")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃 API")
    public ApiResult logout(@RequestBody @Valid AccountDto.LogoutDto dto) throws UserNotFoundException {

        service.logout(dto.username());

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
