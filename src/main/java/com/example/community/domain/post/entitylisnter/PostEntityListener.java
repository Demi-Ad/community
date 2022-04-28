package com.example.community.domain.post.entitylisnter;

import com.example.community.common.util.BeanUtils;
import com.example.community.common.util.StaticPathContext;
import com.example.community.domain.post.entity.Post;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostRemove;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PostEntityListener {

    @PostRemove
    public void postRemoveAfter(Object o) {
        if (o instanceof Post) {
            Post post = (Post) o;
            deletePostImage(post);
            deletePostUploadFile(post);
        }
    }

    private void deletePostImage(Post post) {
        String content = post.getContent();
        List<String> imgSrcList = htmlImgSrcParse(content);
        StaticPathContext staticPathContext = BeanUtils.getBean(StaticPathContext.class);
        String imgPath = staticPathContext.getImgPath();
        imgSrcList.stream()
                .map(s -> {
                    String[] srcSplit = s.split("/");
                    return srcSplit[srcSplit.length - 1];
                })
                .map(s -> new File(imgPath + "//" + s))
                .forEach(File::delete);
    }

    private List<String> htmlImgSrcParse(String html) {
        Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

        List<String> result = new ArrayList<>();
        Matcher matcher = nonValidPattern.matcher(html);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private void deletePostUploadFile(Post post) {
        StaticPathContext staticPathContext = BeanUtils.getBean(StaticPathContext.class);
        post.getPostFiles().stream()
                .map(postFile -> new File(staticPathContext.getFilePath() + postFile.getFileConvertName()))
                .forEach(File::delete);
    }
}
