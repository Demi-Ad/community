package com.example.community.domain.post.dto;

import com.example.community.domain.post.common.SearchParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchParam {
    public SearchParam param;
    public String keyword;

}
