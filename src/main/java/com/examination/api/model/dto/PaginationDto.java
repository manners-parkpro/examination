package com.examination.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationDto {

    private int page;
    private Long totalCount;
}
