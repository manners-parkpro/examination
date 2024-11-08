package com.examination.api.presentation.front.reserve;

import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.LoginUser;
import com.examination.api.model.dto.ReserveDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.front.reserve.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reserve/")
@Tag(name = "예약", description = "호텔 예약 신청 API")
public class ReserveController {

    private final ReserveService service;

    @PostMapping("save/{roomId}")
    @Operation(summary = "객실 예약", description = "객실 예약 정보 저장")
    public ApiResult<ReserveDto.ResponseDto> save(@PathVariable Long roomId, @RequestBody @Valid ReserveDto dto, @AuthenticationPrincipal LoginUser loginUser) throws RequiredParamNonException, NotFoundException {

        return ApiResult.<ReserveDto.ResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.save(roomId, dto, loginUser))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
