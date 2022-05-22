package com.example.community.admin.forbiddenWord.validator;

import com.example.community.admin.forbiddenWord.service.ForbiddenWordService;
import com.example.community.admin.forbiddenWord.service.ForbiddenWordSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ForbiddenWordCheckValidator {

    private final ForbiddenWordService forbiddenWordService;


    public void validate(String validateStr, ForbiddenWordSpecification forbiddenWordSpecification, Errors errors) {
        List<String> forbiddenWordList = forbiddenWordService.getForbiddenWord(forbiddenWordSpecification);

        Set<String> checkedForbiddenWord = new HashSet<>();

        forbiddenWordList.forEach(forbiddenWord -> {
            if (validateStr.contains(forbiddenWord)) {
                checkedForbiddenWord.add(forbiddenWord);
            }
        });

        if (checkedForbiddenWord.size() != 0) {
            errors.reject("forbiddenWordFind",String.join(", ", checkedForbiddenWord) + "는 금지어 입니다");
        }
    }
}
