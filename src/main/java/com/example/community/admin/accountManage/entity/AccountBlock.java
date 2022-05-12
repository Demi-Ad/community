package com.example.community.admin.accountManage.entity;

import com.example.community.domain.account.entity.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account blockAccount;

    private String blockComment;

    @CreatedDate
    private LocalDateTime blockDate;

    private LocalDateTime blockUntilDate;

    public AccountBlock(String blockComment, LocalDateTime blockUntilDate) {
        this.blockComment = blockComment;
        this.blockUntilDate = blockUntilDate;
    }

    public void blockAccount(Account account) {
        this.blockAccount = account;
    }

    public void changeBlock(String blockComment, LocalDateTime blockDate) {
        this.blockComment = blockComment;
        this.blockUntilDate = blockDate;
    }
}
