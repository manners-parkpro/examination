package com.examination.api.filter;

import com.examination.api.model.dto.LoginUser;
import com.examination.api.core.TokenProvider;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final Set<String> paths = new HashSet<>(Arrays.asList("/h2-console/**", "/swagger-ui/**", "/v3/**", "/api/auth/**", "/api/admin/auth/**"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = tokenProvider.resolveToken(request);
        String requestURI = request.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            setAuthentication(authentication.getPrincipal().toString(), jwt, (List<GrantedAuthority>) authentication.getAuthorities());
            log.info("Security Context에 '[{}]' 인증 정보를 저장했습니다, uri: [{}]", authentication.getName(), requestURI);
        } else
            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String principal, String token, List<GrantedAuthority> authorities) {
        LoginUser loginUser = new LoginUser(principal, token, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(@Nonnull HttpServletRequest request) {
        return paths.stream().anyMatch(p -> new AntPathRequestMatcher(p).matches(request));
    }
}
