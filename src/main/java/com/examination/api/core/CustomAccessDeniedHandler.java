package com.examination.api.core;

import com.examination.api.model.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("권한 없는 사용자 접근 " + accessDeniedException.getMessage());
        ApiResult apiResult = ApiResult.builder()
                .code(HttpServletResponse.SC_FORBIDDEN)
                .message("권한 없는 사용자 접근 " + accessDeniedException.getMessage())
                .build();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(apiResult));
        writer.flush();
    }
}
