package com.examination.api.service.auth;

import com.examination.api.exception.AlreadyEntity;
import com.examination.api.exception.RequiredParamNonException;
import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.domain.AccountAuthority;
import com.examination.api.model.domain.RefreshToken;
import com.examination.api.model.dto.TokenDto;
import com.examination.api.model.dto.AccountDto;
import com.examination.api.model.types.ResponseMessage;
import com.examination.api.core.TokenProvider;
import com.examination.api.repository.account.AccountRepository;
import com.examination.api.repository.jwt.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public AccountDto.ResponseDto save(AccountDto.RequestDto dto) throws AlreadyEntity, RequiredParamNonException {
        isDuplicated(dto);

        Account account = Account.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .activeYn(dto.getActiveYn())
                .build();

        setAuthorities(account);

        repository.save(account);

        return AccountDto.ResponseDto.builder()
                .username(account.getUsername())
                .nickname(account.getNickname())
                .build();
    }

    @Transactional
    public TokenDto login(AccountDto.LoginDto dto) throws RequiredParamNonException {
        if (dto == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return getTokenByProvider(authentication, dto.getUsername());
    }

    @Transactional
    public TokenDto reissue(String refreshToken) throws RequiredParamNonException {
        if (!StringUtils.hasText(refreshToken))
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        RefreshToken refresh = refreshTokenRepository.findByRefreshToken(refreshToken);
        if (refresh == null)
            throw new RequiredParamNonException("다시 로그인 후 시도해 주시기 바랍니다.");

        if (!tokenProvider.validateToken(refreshToken) || !refresh.getRefreshToken().equals(refreshToken))
            throw new RuntimeException("RefreshToken이 잘못되었습니다. 확인 후 다시 시도해주세요.");

        return getTokenByProvider(tokenProvider.getAuthentication(refreshToken), refresh.getUsername());
    }

    @Transactional
    public void logout(String username) throws UserNotFoundException {
        Account account = repository.findByUsername(username);
        if (account == null)
            throw new UserNotFoundException(username + "은 존재하지 않는 회원입니다.");

        RefreshToken refreshToken = refreshTokenRepository.findByUsernamd(username);

        if (refreshToken != null)
            refreshTokenRepository.delete(refreshToken);
    }

    private void setAuthorities(Account account) throws RequiredParamNonException {
        if (account == null)
            throw new RequiredParamNonException(ResponseMessage.REQUIRED.getMessage());

        account.getAuthorities().clear();

        List<AccountAuthority> authorities = new ArrayList<>();
        AccountAuthority authority = AccountAuthority.builder()
                .authorityName("ROLE_USER")
                .account(account)
                .build();

        authorities.add(authority);
        account.getAuthorities().addAll(authorities);
    }

    private void isDuplicated(AccountDto.RequestDto dto) throws AlreadyEntity {
        Account findAccount = repository.findByUsername(dto.getUsername());

        if (findAccount != null)
            throw new AlreadyEntity("AlreadyEntity USER");

        findAccount = repository.findByNickname(dto.getNickname());

        if (findAccount != null)
            throw new AlreadyEntity("AlreadyEntity USER NICKNAME");
    }

    private TokenDto getTokenByProvider(Authentication authentication, String username) {
        TokenDto tokenDto = tokenProvider.generateToken(authentication);

        // DB에 Token이 존재 하는지 체크
        RefreshToken refreshToken = refreshTokenRepository.findByUsernamd(username);

        if (refreshToken != null)
            refreshToken.modify(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        else {
            refreshToken = RefreshToken.builder()
                    .username(username)
                    .accessToken(tokenDto.getAccessToken())
                    .refreshToken(tokenDto.getRefreshToken())
                    .createdDate(LocalDateTime.now())
                    .build();
        }

        // Token 정보 Redis 저장
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }
}
