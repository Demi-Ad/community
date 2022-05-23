package com.example.community.admin.forbiddenWord.validator;

import com.example.community.admin.forbiddenWord.service.ForbiddenWordService;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ForbiddenWordCheckValidator {

    private final ForbiddenWordService forbiddenWordService;
    public final String ERROR_NAME = "forbiddenWordFind";

    public void validate(String validateStr, ForbiddenWordSpecification forbiddenWordSpecification, Errors errors) {
        List<String> forbiddenWordList = forbiddenWordService.getForbiddenWord(forbiddenWordSpecification);

        Set<String> checkedForbiddenWord = new HashSet<>();

        forbiddenWordList.forEach(forbiddenWord -> {
            if (validateStr.contains(forbiddenWord)) {
                checkedForbiddenWord.add(forbiddenWord);
            }
        });

        if (checkedForbiddenWord.size() != 0) {
            errors.reject(ERROR_NAME,String.join(", ", checkedForbiddenWord) + "는 금지어 입니다");
        }
    }
}
