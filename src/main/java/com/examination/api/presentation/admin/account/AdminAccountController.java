package com.examination.api.presentation.admin.account;

import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.admin.auth.AdminAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
@Tag(name = "Admin", description = "Admin 회원가입 및 계정 활성화 API")
public class AdminAccountController {

    private final AdminAuthService service;

    @PostMapping("active/{id}")
    @Operation(summary = "ADMIN 계정 활성화 변경", description = "ADMIN 계정 활성화 변경하는 API")
    public ApiResult active(@PathVariable Long id, @RequestBody @Valid AccountDto.ActiveDto dto) throws UserNotFoundException {

        service.active(id, dto.getActiveYn());

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
