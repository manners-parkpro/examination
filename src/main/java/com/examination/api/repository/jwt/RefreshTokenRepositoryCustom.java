package com.examination.api.repository.jwt;

import com.examination.api.model.domain.RefreshToken;

public interface RefreshTokenRepositoryCustom {

    RefreshToken findByUsernamd(String username);
    RefreshToken findByUsernamdAndAccessToken(String username, String accessToken);
    RefreshToken findByUsernamdAndRefreshToken(String username, String token);
    RefreshToken findByRefreshToken(String token);
}
