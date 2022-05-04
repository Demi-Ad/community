package com.example.community.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInformationDto {

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 10)
    private String nickname;
    private MultipartFile profile;
}
