package com.examination.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {

    @Builder.Default
    private String grantType = "Bearer";
    private String accessToken;
    private String refreshToken;
}
