package com.example.community.admin.accountManage.resolver;

import com.example.community.admin.accountManage.dto.AccountBlockRequestDto;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AccountBlockArgResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(BlockArg.class) != null;
        boolean isPostSearchParam = parameter.getParameterType().isAssignableFrom(AccountBlockRequestDto.class);
        return hasAnnotation && isPostSearchParam;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        try {
            long accountId = Long.parseLong(webRequest.getParameter("accountId"));
            String blockComment = webRequest.getParameter("blockComment");

            LocalDate blockDate = LocalDate.parse(webRequest.getParameter("blockDate"));
            LocalTime blockTime;
            try {
                blockTime = LocalTime.parse(webRequest.getParameter("blockTime"));
            } catch (DateTimeParseException e) {
                blockTime = LocalTime.of(0, 0);
            }
            LocalDateTime blockDateTIme = LocalDateTime.of(blockDate,blockTime);
            return new AccountBlockRequestDto(accountId,blockComment,blockDateTIme);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"파라미터가 올바르지 않습니다");
        }


    }
}
