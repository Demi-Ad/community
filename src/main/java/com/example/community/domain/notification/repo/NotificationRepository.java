package com.example.community.domain.notification.repo;

import com.example.community.domain.account.entity.Account;
import com.example.community.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByToAccount(Account toAccount);

    @Query("select count(n) from Notification n where n.toAccount = :account")
    long countByToAccount(@Param("account") Account toAccount);

    @Transactional
    @Modifying
    @Query("delete from Notification n where n.toAccount = :account")
    void deleteByToAccount(@Param("account") Account toAccount);



}