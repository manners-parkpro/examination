package com.examination.api.presentation.admin.hotel;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.NotFoundException;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.dto.HotelDto;
import com.examination.api.model.dto.PageResponseDto;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.service.admin.hotel.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/hotel/")
@Tag(name = "Hotel", description = "호텔 정보 및 호텔 편의시설 저장 및 관리 API")
public class HotelController {

    private final HotelService service;

    @GetMapping("list")
    @Operation(summary = "Hotel 리스트", description = "Hotel 전체 리스트")
    public ApiResult<PageResponseDto> list(HotelDto.PageRequestDto dto) throws UserNotFoundException {

        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getPerPage());

        return ApiResult.<PageResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.readPage(dto, pageable))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("save")
    @Operation(summary = "Hotel 정보 저장", description = "Hotel 정보 저장")
    public ApiResult<HotelDto.HotelResponseDto> save(@RequestBody @Valid HotelDto dto) throws RequiredParamNonException, AlreadyEntity {

        return ApiResult.<HotelDto.HotelResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.save(dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    @PutMapping("put/{id}")
    @Operation(summary = "Hotel 정보 수정", description = "Hotel 정보 수정")
    public ApiResult<HotelDto.HotelResponseDto> put(@PathVariable Long id, @RequestBody @Valid HotelDto dto) throws RequiredParamNonException, AlreadyEntity, NotFoundException {

        return ApiResult.<HotelDto.HotelResponseDto>builder()
                .code(ApiResult.RESULT_CODE_OK)
                .data(service.put(id, dto))
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }

    // 호텔 정보 삭제는 Soft Delete가 아닌 이유는 호텔정보가 없다는건 폐업했다는 가정을 두었기 때문이다.
    @DeleteMapping("delete/{id}")
    @Operation(summary = "Hotel 정보 삭제", description = "Hotel 정보 삭제")
    public ApiResult delete(@PathVariable Long id) throws NotFoundException {

        service.delete(id);

        return ApiResult.builder()
                .code(ApiResult.RESULT_CODE_OK)
                .message(ApiResultCode.SUCCESS.getCode())
                .build();
    }
}
