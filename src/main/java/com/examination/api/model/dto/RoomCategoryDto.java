package com.examination.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCategoryDto {

    private Long id;
    @NotBlank(message = "객실 카테고리 설명을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{2,200}$", message = "객실 카테고리 설명은 한글, 숫자, 영문 최소 2자, 최대 200자로 입력해 주세요.")
    private String category;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomCategoryResponseDto {
        private Long id;
        private String category;
    }
}
