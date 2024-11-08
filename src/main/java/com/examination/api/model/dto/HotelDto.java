package com.examination.api.model.dto;

import com.examination.api.model.types.GradeType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    private Long id;
    @NotBlank(message = "호텔명을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{2,50}$", message = "호텔명은 한글, 숫자, 영문 최소 2자, 최대 50자로 입력해 주세요.")
    private String name;
    @NotBlank(message = "호텔의 연락처를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "호텔의 연락처는 숫자로만 입력이 가능합니다.")
    private String contactNumber;
    @NotBlank(message = "호텔의 지역을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,50}$", message = "호텔명은 한글, 영문 최소 2자, 최대 50자로 입력해 주세요.")
    private String area;
    @NotBlank(message = "호텔주소를 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-\s]{2,100}$", message = "호텔명은 한글, 숫자, 영문 최소 2자, 최대 100자로 입력해 주세요.")
    private String address;
    @NotNull(message = "호텔등급을 선택해주세요.")
    private GradeType grade;
    @Valid
    @NotNull(message = "호텔 편의시설을 입력해 주세요.")
    private List<AmenityDto> amenities; // Valid 체크를 위해 new ArrayList<>(); 선언 제거
    private List<RoomDto> rooms = new ArrayList<>();

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageRequestDto extends BasePageDto {
        private String name;
        private String area;
        private String grade;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelResponseDto {
        private Long id;
        private String name;
        private String contactNumber;
        private String area;
        private String address;
        private String grade;
        private List<AmenityDto.AmenityResponseDto> amenities = new ArrayList<>();
    }
}
