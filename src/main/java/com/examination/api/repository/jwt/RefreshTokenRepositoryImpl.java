package com.examination.api.repository.jwt;

import com.examination.api.model.domain.QRefreshToken;
import com.examination.api.model.domain.RefreshToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private static final QRefreshToken refreshToken = new QRefreshToken("refreshToken");

    @Override
    public RefreshToken findByUsernamd(String username) {
        return jpaQueryFactory
                .selectFrom(refreshToken)
                .where(refreshToken.username.eq(username))
                .fetchFirst();
    }

    @Override
    public RefreshToken findByUsernamdAndAccessToken(String username, String accessToken) {
        return jpaQueryFactory
                .selectFrom(refreshToken)
                .where(refreshToken.username.eq(username), refreshToken.accessToken.eq(accessToken))
                .fetchFirst();
    }

    @Override
    public RefreshToken findByUsernamdAndRefreshToken(String username, String token) {
        return jpaQueryFactory
                .selectFrom(refreshToken)
                .where(refreshToken.username.eq(username), refreshToken.refreshToken.eq(token))
                .fetchFirst();
    }

    @Override
    public RefreshToken findByRefreshToken(String token) {
        return jpaQueryFactory
                .selectFrom(refreshToken)
                .where(refreshToken.refreshToken.eq(token))
                .fetchFirst();
    }
}
