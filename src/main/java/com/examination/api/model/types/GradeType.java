package com.examination.api.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GradeType {

    GRADE_1("★"),
    GRADE_2("★★"),
    GRADE_3("★★★"),
    GRADE_4("★★★★"),
    GRADE_5("★★★★★");

    private final String description;
}
