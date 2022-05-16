package com.example.community.domain.account.repo;

import com.example.community.admin.accountManage.dto.AccountDto;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.projection.AccountInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    boolean existsByEmailEquals(String email);


    Optional<Account> findByEmail(String email);

    Optional<Account> findFirstByEmailEquals(String email);

    @Query("select a from Account a where a.nickname like %:nickname% and a.isEmailVerified = true")
    Page<Account> findByNicknameContaining(@Param("nickname") String nickname, Pageable pageable);


    Optional<AccountInfoProjection> searchById(Long id);

    @Query(value = "select new com.example.community.admin.accountManage.dto.AccountDto(a.id,a.email,a.isEmailVerified,(count(ab.id) > 0),a.nickname,a.registeredAt) " +
            "from Account a left join AccountBlock ab on a.id = ab.blockAccount.id group by a.id, ab.id")
    Page<AccountDto> listManageAccount(Pageable pageable);


}