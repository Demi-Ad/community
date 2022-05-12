package com.example.community.admin.accountManage.repo;

import com.example.community.admin.accountManage.entity.AccountBlock;
import com.example.community.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountBlockRepository extends JpaRepository<AccountBlock, Long> {
    boolean existsByBlockAccount(Account blockAccount);

    Optional<AccountBlock> findByBlockAccount(Account blockAccount);

    @Query(value = "delete from AccountBlock ab where ab.blockAccount.id = :id")
    @Transactional
    @Modifying
    void deleteBlockAccount(@Param("id") Long id);

}