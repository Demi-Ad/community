package com.example.community.domain.account.common;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class DuplicateEmailValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDto registerDto = (RegisterDto) target;
        String email = registerDto.getEmail();
        if (accountRepository.existsByEmail(email)) {
            errors.reject("이메일 중복");
        }
    }
}
