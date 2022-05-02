package com.example.community.domain.account.repo;

import com.example.community.domain.account.dto.AccountInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class AccountInfoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private AccountInfoDto rowMapping(ResultSet rs, int count) throws SQLException {
        AccountInfoDto dto = new AccountInfoDto();
        dto.setNickname(rs.getString("nickname"));
        dto.setEmail(rs.getString("email"));
        dto.setProfile(rs.getString("profile"));
        dto.setPostWriteCount(rs.getInt("postWriteCount"));
        dto.setCommentWriteCount(rs.getInt("commentWriteCount"));
        return dto;
    }

    public AccountInfoDto projectionAccountInfo(Long userId) {

        String sql = "select a.nickname as nickname , a.email as email, a.profile_img as profile, " +
                "(select count(p.post_id) from Post p where p.account_id = :userId) as postWriteCount, " +
                "(select count(c.comment_id) from Comment c where c.account_id = :userId) as commentWriteCount " +
                "from Account a where a.account_id = :userId";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return jdbcTemplate.queryForObject(sql, mapSqlParameterSource, this::rowMapping);

    }
}