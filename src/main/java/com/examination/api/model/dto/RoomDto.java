package com.examination.api.model.dto;

import com.examination.api.model.types.YNType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;
    // 객실 이름
    @NotBlank(message = "객실 이름을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{2,50}$", message = "객실명은 한글, 숫자, 영문 최소 2자, 최대 50자로 입력해 주세요.")
    private String name;
    // 객실 설명
    @NotBlank(message = "객실 이름을 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9\s]{2,200}$", message = "객실 설명은 한글, 숫자, 영문 최소 2자, 최대 200자로 입력해 주세요.")
    private String description;
    // 방 공간 (㎡)
    @NotBlank(message = "객실 면적을 입력해주세요.")
    @Pattern(regexp = "^[0-9]{2,3}$", message = "객실면적은 숫자로만 최대 3자 입력해 주세요.")
    private String size;
    // 인원제한
    @NotNull(message = "객실 인원을 입력해주세요.")
    @Max(8)
    private Integer people;
    // 가격
    @NotNull(message = "객실 가격을 입력해주세요.")
    @Min(message = "객실가격은 최소 10,000원 이상이여야 합니다.", value = 10000)
    @Max(message = "객실가격은 최소 100,000,000원 이하이여야 합니다.", value = 100000000)
    private BigDecimal price;
    // Category
    @Valid
    @NotNull(message = "객실 카테고리를 입력해주세요.")
    private List<RoomCategoryDto> roomCategories = new ArrayList<>();

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomResponseDto {
        private Long id;
        private String name;
        private String description;
        // 방 공간 (㎡)
        private String size;
        private Integer people;
        private String price;
        private List<RoomCategoryDto.RoomCategoryResponseDto> roomCategories = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomUsableDto {
        private YNType deleteYn;
    }
}
