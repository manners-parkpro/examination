package com.examination.api.model.dto;

import com.examination.api.model.types.YNType;
import com.examination.api.validation.EmailValid;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @Builder
    public record LoginDto(
            @NotBlank(message = "아이디를 입력해주세요.")
            @EmailValid(message = "Username이 올바르지 않은 형식입니다.")
            String username,
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password
    ) {}

    @Builder
    public record LogoutDto(
        @NotBlank(message = "아이디를 입력해주세요.")
        @EmailValid(message = "Username이 올바르지 않은 형식입니다.")
        String username
    ) {}

    @Builder
    public record RequestDto(

        @NotBlank(message = "Username이 입력해주세요.")
        @EmailValid(message = "Username이 올바르지 않은 형식입니다.")
        String username,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        String password,
        @NotBlank(message = "Nickname을 입력해주세요.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,10}$", message = "Nickname은 한글, 영문 최소 2자, 최대 10자로 입력해 주세요.")
        String nickname,
        @JsonIgnore
        YNType activeYn
    ) {}

    @Builder
    public record ResponseDto(
            String username,
            String nickname,
            @JsonInclude(NON_NULL)
            List<AccountAuthorityDto> authorities
    ) {}

    @Builder
    public record ActiveDto(
        @NotNull(message = "활성화 여부를 입력해주세요.")
        YNType activeYn
    ) {}
}
