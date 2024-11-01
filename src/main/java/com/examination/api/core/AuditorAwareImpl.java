package com.examination.api.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            try {
                Map<String, Object> principalMap = (Map<String, Object>) authentication.getPrincipal();
                return Optional.of(principalMap.get("username").toString());
            } catch (Exception e) {
                return Optional.of(authentication.getName());
            }
        } else {
            log.debug("Not Authenticated");
            return Optional.empty();
        }
    }
}
