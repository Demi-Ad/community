package com.example.community.domain.account.validator;

import com.example.community.config.security.util.SecurityContextUtil;
import com.example.community.domain.account.dto.AccountSecessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class EmailEqualValidator implements Validator {

    private final SecurityContextUtil securityContextUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountSecessionDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountSecessionDto accountSecessionDto = (AccountSecessionDto) target;
        boolean emailEqual = securityContextUtil.currentAccount().getEmail().equals(accountSecessionDto.getEmailConfirm());
        if (!emailEqual)
            errors.rejectValue("emailConfirm","email.notEqual","이메일이 일치하지 않습니다");
    }
}
