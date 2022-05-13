package com.example.community.admin.postManage.spec;

import com.example.community.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchParam {
    private SearchFiled searchFiled;
    private String searchValue;

    public Specification<Post> get() {
        if (searchFiled == null || searchValue == null)
            return null;
        return searchFiled.search(searchValue);
    }


}
