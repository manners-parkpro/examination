package com.examination.api.presentation.admin.room;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.HotelDto;
import com.examination.api.model.dto.RoomDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.admin.room.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/room/")
public class RoomConrtoller {

    private final RoomService service;

    @PostMapping("save/{hotelId}")
    @Operation(summary = "객실 정보 저장", description = "객실 정보 저장")
    public ApiResult<HotelDto.HotelResponseDto> save(@PathVariable Long hotelId, @RequestBody @Valid RoomDto dto) throws RequiredParamNonException, NotFoundException, AlreadyEntity {

        return ApiResult.<HotelDto.HotelResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.save(hotelId, dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PutMapping("put/{id}")
    @Operation(summary = "객실 정보 수정", description = "객실 정보 수정")
    public ApiResult put(@PathVariable Long id, @RequestBody @Valid RoomDto dto) throws RequiredParamNonException, NotFoundException, AlreadyEntity {

        service.put(id, dto);

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PatchMapping("usable/{id}")
    @Operation(summary = "객실 삭제여부 변경", description = "객실 삭제여부 변경")
    public ApiResult usable(@PathVariable Long id, @RequestBody RoomDto.RoomUsableDto dto) throws NotFoundException {

        service.usable(id, dto.getDeleteYn());

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
