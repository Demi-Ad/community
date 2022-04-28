package com.example.community.domain.account.repo;

import com.example.community.domain.account.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findFirstByEmailEquals(String email);

    Page<Account> findByNicknameContaining(String nickname, Pageable pageable);







}