package com.example.community.admin.user.entity;


import com.example.community.config.security.Role;
import lombok.AccessLevel;
import lombok.Builder;
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
public class Admin {

    @Id
    @GeneratedValue
    private Long seq;

    private String adminId;

    private String password;

    private String adminName;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public Admin(String adminId, String password, String adminName, Role role) {
        this.adminId = adminId;
        this.password = password;
        this.adminName = adminName;
        this.role = role;
    }
}
