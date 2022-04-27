package com.example.community.domain.post.resolver;

import com.example.community.domain.post.common.SearchParam;
import com.example.community.domain.post.dto.PostSearchParam;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;


public class PostSearchParamArgResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(Search.class) != null;
        boolean isPostSearchParam = parameter.getParameterType().isAssignableFrom(PostSearchParam.class);
        return hasAnnotation && isPostSearchParam;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        try {
            String param = webRequest.getParameter("param");
            String keyword = webRequest.getParameter("keyword");
            SearchParam searchParam = SearchParam.of(param);

            if (searchParam == SearchParam.TAG)
                keyword = "#" + keyword;

            return new PostSearchParam(searchParam,keyword);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"파라미터가 올바르지 않습니다");
        }

    }
}
