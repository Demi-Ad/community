package com.example.community.domain.account.repo.impl;

import com.example.community.domain.account.dto.AccountInfoDetailDto;
import com.example.community.domain.account.repo.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Profile("h2")
@Repository("accountInfoRepository")
public class H2AccountInfoRepository implements AccountInfoRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<AccountInfoDetailDto> projectionAccountInfo(Long userId) {

        String sql = "select a.nickname as nickname , a.email as email, a.profile_img as profile, " +
                "(select count(p.post_id) from Post p where p.account_id = :userId) as postWriteCount, " +
                "(select count(c.comment_id) from Comment c where c.account_id = :userId) as commentWriteCount, " +
                "(select count(g.guest_book_id) from guest_book g where g.guest_id = :userId) as guestBookWriteCount " +
                "from Account a where a.account_id = :userId";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        try {
            AccountInfoDetailDto accountInfoDetailDto = jdbcTemplate.queryForObject(sql, mapSqlParameterSource, this::rowMapping);
            return Optional.ofNullable(accountInfoDetailDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}