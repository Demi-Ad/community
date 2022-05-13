package com.example.community.admin.postManage.resolver;

import com.example.community.admin.postManage.spec.SearchFiled;
import com.example.community.admin.postManage.spec.SearchParam;
import com.example.community.admin.postManage.spec.SearchPost;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SearchFieldResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(SearchPost.class) != null;
        boolean isPostSearchParam = parameter.getParameterType().isAssignableFrom(SearchParam.class);
        return hasAnnotation && isPostSearchParam;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String filed = webRequest.getParameter("filed");
        String searchValue = webRequest.getParameter("searchValue");
        return new SearchParam(SearchFiled.of(filed), searchValue);
    }
}
