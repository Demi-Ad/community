package com.example.community.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto implements Serializable {

    @NotNull
    @NotEmpty
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotEmpty
    @NotNull
    private String password;


    private MultipartFile profileImg;
    @NotEmpty
    @Size(min = 2, message = "최소 2글자 이상이여야 합니다")
    private String nickname;

}
