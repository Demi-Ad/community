package com.example.community.common.mailing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String mailAddress;
    private String title;
    private String message;

}
