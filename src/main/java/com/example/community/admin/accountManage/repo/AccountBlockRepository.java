package com.example.community.admin.accountManage.repo;

import com.example.community.admin.accountManage.entity.AccountBlock;
import com.example.community.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBlockRepository extends JpaRepository<AccountBlock, Long> {
    boolean existsByBlockAccount(Account blockAccount);

    Optional<AccountBlock> findByBlockAccount(Account blockAccount);

}