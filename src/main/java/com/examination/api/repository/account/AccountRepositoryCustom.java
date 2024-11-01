package com.examination.api.repository.account;

import com.examination.api.model.domain.Account;

public interface AccountRepositoryCustom {

    Account findByUsername(String username);
    Account findByNickname(String nickname);
}
