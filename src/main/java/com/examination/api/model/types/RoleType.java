package com.examination.api.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleType {

    ROLE_USER("사용자"),
    ROLE_ADMIN("어드민");

    private String description;
}
