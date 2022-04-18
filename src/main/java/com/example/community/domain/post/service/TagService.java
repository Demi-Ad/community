package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.TagDto;
import com.example.community.domain.post.entity.Tag;
import com.example.community.domain.post.repo.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> saveElseFind(List<TagDto> tagDtoList) {
        List<Tag> tagList = new ArrayList<>();

        for (TagDto tagDto : tagDtoList) {
            String item = tagDto.getItem();
            Tag tag = tagRepository.findByItem(item).orElseGet(() -> new Tag(item));
            if (tag.getId() == null)
                tagList.add(tag);
        }
        return tagList;
    }
}
