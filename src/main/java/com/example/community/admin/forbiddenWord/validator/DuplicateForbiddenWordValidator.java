package com.example.community.admin.forbiddenWord.validator;

import com.example.community.admin.forbiddenWord.dto.ForbiddenWordDto;
import com.example.community.admin.forbiddenWord.entity.ForbiddenWord;
import com.example.community.admin.forbiddenWord.repo.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuplicateForbiddenWordValidator implements Validator {
    private final ForbiddenWordRepository forbiddenWordRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ForbiddenWordDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ForbiddenWordDto forbiddenWordDto = (ForbiddenWordDto) target;
        String forbiddenWord = forbiddenWordDto.getForbiddenWord();
        boolean duplicateCheck = forbiddenWordRepository.existsByForbiddenText(forbiddenWord);

        if (duplicateCheck) {
            errors.rejectValue("forbiddenWord","duplicate.forbidden","이미 같은 값이 존재합니다");
        }
    }
}
