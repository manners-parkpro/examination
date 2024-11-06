package com.examination.api.service.admin.auth;

import com.examination.api.core.TokenProvider;
import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.domain.AccountAuthority;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.model.types.RoleType;
import com.examination.api.model.types.YNType;
import com.examination.api.repository.account.AccountRepository;
import com.examination.api.repository.jwt.RefreshTokenRepository;
import com.examination.api.service.front.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .activeYn(YNType.N)
                .build();

        setAdminAuthorities(account);

        repository.save(account);

        return AccountDto.ResponseDto.builder()
                .username(account.getUsername())
                .nickname(account.getNickname())
                .build();
    }

    public void active(Long id, String activeYn) throws UserNotFoundException {
        Account account = repository.findById(id).orElseThrow(() -> new UserNotFoundException("UserNotFoundException("));
        account.modifyActiveYn("Y".equals(activeYn) ? YNType.Y : YNType.N);
    }

    private void setAdminAuthorities(Account account) throws RequiredParamNonException {
        if (account == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        account.getAuthorities().clear();

        List<AccountAuthority> authorities = new ArrayList<>();
        AccountAuthority authority = AccountAuthority.builder()
                .authorityName(RoleType.ROLE_ADMIN)
                .account(account)
                .build();

        authorities.add(authority);
        account.getAuthorities().addAll(authorities);
    }
}
