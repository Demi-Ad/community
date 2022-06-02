package com.example.community.domain.account.repo;

import com.example.community.domain.account.dto.AccountInfoDetailDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public interface AccountInfoRepository {
    Optional<AccountInfoDetailDto> projectionAccountInfo(Long userId);

    default AccountInfoDetailDto rowMapping(ResultSet rs, int count) throws SQLException {
        AccountInfoDetailDto dto = new AccountInfoDetailDto();
        dto.setNickname(rs.getString("nickname"));
        dto.setProfile(rs.getString("profile"));
        dto.setPostWriteCount(rs.getInt("postWriteCount"));
        dto.setCommentWriteCount(rs.getInt("commentWriteCount"));
        dto.setGuestBookWriteCount(rs.getInt("guestBookWriteCount"));
        return dto;
    }
}