package com.example.community.domain.account.validator;

import com.example.community.domain.account.dto.RegisterDto;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public void validate(Object target, Errors errors) {
        RegisterDto registerDto = (RegisterDto) target;
        String email = registerDto.getEmail();
        if (accountRepository.existsByEmailEquals(email)) {
            errors.rejectValue("email","email.duplicate","아이디 중복");
        }
    }
}
