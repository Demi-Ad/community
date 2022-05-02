package com.example.community.domain.account.repo;

import com.example.community.domain.account.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmailEquals(String email);


    Optional<Account> findByEmail(String email);

    Optional<Account> findFirstByEmailEquals(String email);

    @Query("select a from Account a where a.nickname like %:nickname% and a.lock = false")
    Page<Account> findByNicknameContaining(@Param("nickname") String nickname, Pageable pageable);

}