package com.examination.api.presentation.front.reserve;

import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
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

import java.util.List;

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

    @DeleteMapping("cancel/{roomId}")
    @Operation(summary = "객실 예약 취소", description = "객실 예약 취소")
    public ApiResult cancel(@PathVariable Long roomId, @AuthenticationPrincipal LoginUser loginUser) throws RequiredParamNonException, NotFoundException {

        service.cancel(roomId, loginUser);

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @GetMapping("reservation")
    @Operation(summary = "호텔 예약 정보", description = "호텔 예약 상세정보 API")
    public ApiResult<List<ReserveDto.ResponseDto>> reservation(@AuthenticationPrincipal LoginUser loginUser) throws UserNotFoundException {

        return ApiResult.<List<ReserveDto.ResponseDto>>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.findReservation(loginUser.getPrincipal()))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
