package com.examination.api.service.front.account;

import com.examination.api.exception.UserNotFoundException;
import com.examination.api.model.domain.Account;
import com.examination.api.model.types.YNType;
import com.examination.api.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository repository;

    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {

        Account account = repository.findByUsername(username);

        if (account == null)
            throw new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다.");

        return createUser(username, account);
    }

    private User createUser(String username, Account account) {
        if (YNType.N.equals(account.getActiveYn()))
            throw new RuntimeException(username + " -> 계정은 활성화되어 있지 않습니다.");

        List<GrantedAuthority> grantedAuthorities = account.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName().toString()))
                .collect(Collectors.toList());

        return new User(username, account.getPassword(), grantedAuthorities);
    }
}
