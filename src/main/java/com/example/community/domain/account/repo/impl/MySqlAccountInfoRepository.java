package com.example.community.domain.account.repo.impl;

import com.example.community.domain.account.dto.AccountInfoDto;
import com.example.community.domain.account.repo.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Profile("local")
@Repository("accountInfoRepository")
public class MySqlAccountInfoRepository implements AccountInfoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public Optional<AccountInfoDto> projectionAccountInfo(Long userId) {

        String sql = "select a.nickname as nickname , a.email as email, a.profile_img as profile, " +
                "(select count(p.post_id) from post p where p.account_id = :userId) as postWriteCount, " +
                "(select count(c.comment_id) from comment c where c.account_id = :userId) as commentWriteCount " +
                "from account a where a.account_id = :userId";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        AccountInfoDto accountInfoDto = jdbcTemplate.queryForObject(sql, mapSqlParameterSource, this::rowMapping);
        return Optional.ofNullable(accountInfoDto);

    }
}
