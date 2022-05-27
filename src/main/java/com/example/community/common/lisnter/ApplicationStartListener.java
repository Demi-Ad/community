package com.example.community.common.lisnter;


import com.example.community.admin.user.entity.Admin;
import com.example.community.admin.user.repo.AdminRepository;
import com.example.community.common.util.BeanUtils;
import com.example.community.config.security.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@Transactional
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final String profileSavePath;
    private final String imageSavePath;
    private final String uploadFileSavePath;

    private final ResourceLoader resourceLoader;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    public ApplicationStartListener(@Value("${static.profile.save-path}") String profileSavePath,
                                    @Value("${static.postImg.save-path}") String imageSavePath,
                                    @Value("${static.upload.save-path}") String uploadFileSavePath,
                                    ResourceLoader resourceLoader,
                                    AdminRepository adminRepository,
                                    PasswordEncoder passwordEncoder) {
        this.profileSavePath = profileSavePath;
        this.imageSavePath = imageSavePath;
        this.uploadFileSavePath = uploadFileSavePath;
        this.resourceLoader = resourceLoader;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Start!");
        File profileSaveFolder = new File(profileSavePath);
        File imageSaveFolder = new File(imageSavePath);
        File uploadFileSaveFolder = new File(uploadFileSavePath);
        if (!profileSaveFolder.exists() || !profileSaveFolder.isDirectory()) {
            profileSaveFolder.mkdir();
            log.info("profile Folder init");

            try {
                InputStream inputStream = resourceLoader.getResource("classpath:static/temp/person.png").getInputStream();
                Path path = Paths.get(profileSavePath, "person.png");
                Files.copy(inputStream, path);
                log.info("File copy");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!imageSaveFolder.exists() || !imageSaveFolder.isDirectory()) {
            log.info("image Folder init");
            imageSaveFolder.mkdir();
        }

        if (!uploadFileSaveFolder.exists() || !uploadFileSaveFolder.isDirectory()) {
            log.info("upload Folder init");
            uploadFileSaveFolder.mkdir();
        }

        adminRepository.findByAdminId("admin").ifPresentOrElse(admin -> {}, () -> {
            Admin admin = Admin.builder()
                    .adminSeq(1L)
                    .adminId("admin")
                    .password(passwordEncoder.encode("admin"))
                    .adminName("전체관리자")
                    .build();
            adminRepository.save(admin);
        });
    }
}
