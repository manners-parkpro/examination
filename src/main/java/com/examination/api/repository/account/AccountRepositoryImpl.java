package com.examination.api.repository.account;

import com.examination.api.model.domain.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.examination.api.model.domain.QAccount.account;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Account findByUsername(String username) {
        return jpaQueryFactory
                .selectFrom(account)
                .where(account.username.eq(username))
                .fetchOne();
    }

    @Override
    public Account findByNickname(String nickname) {
        return jpaQueryFactory
                .selectFrom(account)
                .where(account.nickname.eq(nickname))
                .fetchOne();
    }
}
