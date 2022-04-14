package com.example.community.domain.account.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailValidateQuery {
    private Long id;
    private String sha256;


    public String toUrl() {
        return "?id=" + id + "&" + "sha256=" + sha256;
    }

    public static EmailValidateQuery of(Long id, String sha256) {
        return new EmailValidateQuery(id,sha256);
    }
}
