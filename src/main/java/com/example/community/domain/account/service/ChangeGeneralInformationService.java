package com.example.community.domain.account.service;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.dto.ChangeInformationDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ChangeGeneralInformationService {

    private final AccountRepository accountRepository;
    private final AccountProfileImageService accountProfileImageService;

    public void changeInformation(AccountDetail accountDetail, ChangeInformationDto changeInformationDto) {
        Account loginAccount = accountDetail.getAccount();
        changeProfile(loginAccount, changeInformationDto.getProfile());
        changeNickName(loginAccount, changeInformationDto.getNickname());

        accountRepository.save(loginAccount);
    }

    private void changeProfile(Account account, MultipartFile profile) {
        if (profile.isEmpty()) {
            return;
        }
        String profileImgPath = accountProfileImageService.profileImageSave(profile);
        account.changeProfileImg(profileImgPath);
    }

    private void changeNickName(Account account, String nickName) {
        if (StringUtils.hasText(nickName)) {
            account.changeNickname(nickName);
        }
    }
}
