package com.example.community.domain.account.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailValidateQuery {
    @NotNull
    private Long id;
    @NotNull
    @NotEmpty
    private String sha256;

    public static EmailValidateQuery of(Long id, String sha256) {
        return new EmailValidateQuery(id,sha256);
    }

    public boolean validate(String sha256) {
        return this.sha256.equals(sha256);
    }

    public String toUri() {
        return "/sign?id=" + id + "&" + "sha256=" + sha256;
    }
}
