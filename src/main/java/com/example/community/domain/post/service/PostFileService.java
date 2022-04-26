package com.example.community.domain.post.service;

import com.example.community.domain.post.dto.DownloadFileDto;
import com.example.community.domain.post.entity.Post;
import com.example.community.domain.post.entity.PostFile;
import com.example.community.domain.post.repo.PostFilesRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PostFileService {

    private final String savePath;
    private final PostFilesRepository postFilesRepository;

    public PostFileService(@Value("${static.upload.save-path}") String savePath, PostFilesRepository postFilesRepository) {
        this.savePath = savePath;
        this.postFilesRepository = postFilesRepository;
    }

    public void save(List<MultipartFile> uploadFiles, Post post) {
        for (MultipartFile uploadFile : uploadFiles) {
            if (uploadFile.isEmpty())
                continue;
            Map<String, String> fileNameMap = parseFileNameExtension(uploadFile.getOriginalFilename());
            PostFile postFile = createPostFile(fileNameMap);
            post.addPostFile(postFile);
            uploadFile(uploadFile,postFile.getFileConvertName());
        }
    }

    public DownloadFileDto findFile(String name) {
        PostFile postFile = postFilesRepository.findByFileConvertNameEquals(name).orElseThrow();
        String fileConvertName = postFile.getFileConvertName();

        try {
            UrlResource resource = new UrlResource("file:" + savePath + fileConvertName);
            return new DownloadFileDto(resource, postFile.getFileOriginName());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private PostFile createPostFile(Map<String, String> fileNameMap) {
        String originName = fileNameMap.get("origin");
        log.info("origin = {}",originName);
        String extension = fileNameMap.get("extension");
        String uuid = UUID.randomUUID().toString();

        return new PostFile(originName,uuid + "." + extension, extension);
    }

    private void uploadFile(MultipartFile multipartFile, String convertName) {
        File file = new File(savePath + convertName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("file upload err",e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> parseFileNameExtension(String fileName) {
        Map<String,String> fileNameMap = new HashMap<>();
        String extension = FilenameUtils.getExtension(fileName);
        String originName = FilenameUtils.getName(fileName);
        fileNameMap.put("origin",originName);
        fileNameMap.put("extension",extension);
        return fileNameMap;
    }
}
