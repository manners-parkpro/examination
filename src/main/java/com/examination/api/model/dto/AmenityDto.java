package com.examination.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDto {

    private Long id;
    @NotBlank(message = "편의 시설의 타이틀을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z\s]{2,50}$", message = "타이틀은 한글, 영문 최소 2자, 최대 50자로 입력해 주세요.")
    private String title;
    @NotBlank(message = "편의 시설의 설명을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_+,./'{}()!~@#$%^&*=\s]{2,200}$", message = "설명은 한글, 숫자, 영문, 특수문자(.’{}~()!@#$%^&*-_=+,/) 최소 2자, 최대 200자로 입력해 주세요.")
    private String description;
    @NotBlank(message = "편의 시설의 위치를 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-\s]{2,30}$", message = "위치는 한글, 숫자, 영문 최소 2자, 최대 30자로 입력해 주세요.")
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime manageStartTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime manageEndTime;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AmenityResponseDto {
        private String title;
        private String description;
        private String location;
        private String manageTime;
    }
}
