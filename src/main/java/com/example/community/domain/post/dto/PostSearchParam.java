package com.example.community.domain.post.dto;

import com.example.community.domain.post.common.SearchParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchParam {
    private SearchParam param;
    private String keyword;

    public String toUri(String result) {
        if (param == SearchParam.TAG)
            return "/search?" + "param=" + param.name() + "&" + "keyword=" + result.replace("#","");

        return "/search?" + "param=" + param.name() + "&" + "keyword=" + result;
    }
}
