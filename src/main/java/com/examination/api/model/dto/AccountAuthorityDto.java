package com.examination.api.model.dto;

import com.examination.api.model.types.RoleType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAuthorityDto {

    private RoleType authorityName;
}
