package com.examination.api.model.dto;

import com.examination.api.core.CryptoConverter;
import com.examination.api.model.domain.Account;
import com.examination.api.model.domain.Hotel;
import com.examination.api.model.domain.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveDto {

    private Long id;
    @NotBlank(message = "신청자 이름을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z\s]{2,100}$", message = "신청자 이름은 한글, 영문 최소 2자, 최대 100자로 입력해 주세요.")
    private String name;
    @NotBlank(message = "신청자의 영문 이름을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z\s]{2,100}$", message = "신청자의 영문 이름은 영문 최소 2자, 최대 100자로 입력해 주세요.")
    private String englishName;
    @NotBlank(message = "연락처를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "연락처는 숫자로만 입력이 가능합니다.")
    private String phone;
    // 예약 시작 일자
    @NotNull(message = "예약 일자 선택해주세요.")
    @FutureOrPresent(message = "예약 일자는 현재 또는 미래 날짜로 선택해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate reserveStartDate;
    // 예약 종료 일자
    @NotNull(message = "예약 종료 일자 선택해주세요.")
    @FutureOrPresent(message = "예약 종료 일자는 미래 날짜로 선택해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate reserveEndDate;
    // 신청 인원
    @Min(value = 1, message = "최소 1명 이상이여야 합니다. 인원을 확인해주세요.")
    private int people;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDto {
        private String name;
        private String reserveDate;
        private int people;
    }
}
