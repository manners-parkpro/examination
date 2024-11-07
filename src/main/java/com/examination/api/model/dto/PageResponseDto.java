package com.examination.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageResponseDto {

    public List<?> contents;

    public PaginationDto pagination;

    @Builder
    public PageResponseDto(List<?> contents, Pageable pageable, Long totalCount) {
        this.contents = contents;
        this.pagination = new PaginationDto(pageable.getPageNumber() + 1, totalCount);
    }
}
