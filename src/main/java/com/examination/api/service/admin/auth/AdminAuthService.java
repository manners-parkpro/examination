package com.examination.api.service.admin.auth;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.RoleType;
import com.examination.api.model.types.YNType;
import com.examination.api.repository.account.AccountRepository;
import com.examination.api.service.front.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AccountRepository repository;
    private final AuthService service;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDto.ResponseDto save(AccountDto.RequestDto dto) throws AlreadyEntity, RequiredParamNonException {
        service.isDuplicated(dto);

        Account account = Account.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .nickname(dto.nickname())
                .activeYn(YNType.N)
                .build();

        service.setAuthorities(account, RoleType.ROLE_ADMIN);

        repository.save(account);

        return AccountDto.ResponseDto.builder()
                .username(account.getUsername())
                .nickname(account.getNickname())
                .build();
    }

    public void active(Long id, YNType activeYn) throws UserNotFoundException {
        Account account = repository.findById(id).orElseThrow(() -> new UserNotFoundException("UserNotFoundException"));
        account.modifyActiveYn(activeYn);
        repository.save(account);
    }
}
