package com.examination.api.core;

import com.examination.api.model.dto.ApiResult;
import com.examination.api.model.types.ApiResultCode;
import com.examination.api.model.types.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.xml.stream.events.Characters;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("미인증 사용자 접근 " + authException.getMessage() + "::" + request.getAttribute("exception"));

        String result = objectMapper.writeValueAsString(ApiResult.builder()
                .code(ApiResult.RESULT_CODE_BAD_UNAUTHORIZED)
                .message(ResponseMessage.UNAUTHORIZED.getMessage())
                .build());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result);
    }
}
