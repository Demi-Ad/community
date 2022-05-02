package com.example.community.domain.account.repo;

import com.example.community.domain.account.dto.AccountInfoDto;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface AccountInfoRepository {
    AccountInfoDto projectionAccountInfo(Long userId);

    default AccountInfoDto rowMapping(ResultSet rs, int count) throws SQLException {
        AccountInfoDto dto = new AccountInfoDto();
        dto.setNickname(rs.getString("nickname"));
        dto.setEmail(rs.getString("email"));
        dto.setProfile(rs.getString("profile"));
        dto.setPostWriteCount(rs.getInt("postWriteCount"));
        dto.setCommentWriteCount(rs.getInt("commentWriteCount"));
        return dto;
    }
}