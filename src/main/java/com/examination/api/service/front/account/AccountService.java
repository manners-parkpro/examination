package com.examination.api.service.front.account;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.dto.AccountAuthorityDto;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AccountDto.ResponseDto findByUsername(String username) throws UserNotFoundException {
        Account account = findAccount(username);

        return AccountDto.ResponseDto.builder()
                .username(account.getUsername())
                .nickname(account.getNickname())
                .authorities(account.getAuthorities().stream().map(item -> AccountAuthorityDto.builder()
                        .authorityName(item.getAuthorityName())
                        .build()).toList())
                .build();
    }

    @Transactional
    public void modifyPassword(String username, String password) throws UserNotFoundException, AlreadyEntity {
        Account account = findAccount(username);

        if (passwordEncoder.matches(password, account.getPassword()))
            throw new AlreadyEntity("기존 비밀번호와 같을 수 없습니다.");

        account.modifyPassword(passwordEncoder.encode(password));
    }

    @Transactional(readOnly = true)
    public Account findAccount(String username) throws UserNotFoundException {
        Account account = repository.findByUsername(username);

        if (account == null)
            throw new UserNotFoundException(username + "을(를) 찾을수 없습니다.");

        return account;
    }
}
