package com.example.community.domain.postFile.controller;

import com.example.community.domain.postFile.dto.DownloadFileDto;
import com.example.community.domain.postFile.service.PostFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class PostFileDownloadController {
    private final PostFileService postFileService;

    @GetMapping("/download/{name}")
    public ResponseEntity<Resource> download(@PathVariable String name) {
        try {
            DownloadFileDto downloadFileDto = postFileService.findFile(name);
            String encodedUploadFileName = UriUtils.encode(downloadFileDto.getOriginName(), StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(downloadFileDto.getResource());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
